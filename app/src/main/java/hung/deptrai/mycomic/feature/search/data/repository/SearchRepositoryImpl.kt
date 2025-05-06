package hung.deptrai.mycomic.feature.search.data.repository

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.data.dto.DTOject
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.mapper.AuthorDTOtoAuthorSearch
import hung.deptrai.mycomic.core.domain.mapper.ScanlationGrouptoScanlationSearch
import hung.deptrai.mycomic.core.domain.mapper.UserDTOtoUserSearch
import hung.deptrai.mycomic.core.domain.mapper.mangaDTOtoMangaSearch
import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorAttributes
import hung.deptrai.mycomic.feature.search.data.local.datasource.TagLocalDataSource
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchAuthorDataSource
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchComicDataSource
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchScanlationGroupDataSource
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchUserDataSource
import hung.deptrai.mycomic.feature.search.domain.SearchType
import hung.deptrai.mycomic.feature.search.domain.model.AuthorSearch
import hung.deptrai.mycomic.feature.search.domain.model.ScanlationGroupSearch
import hung.deptrai.mycomic.feature.search.domain.model.SearchComic
import hung.deptrai.mycomic.feature.search.domain.repository.SearchRepository
import hung.deptrai.mycomic.feature.search.domain.repository.SearchTagRepository
import hung.deptrai.mycomic.feature.search.domain.repository.TokenRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import hung.deptrai.mycomic.core.domain.wrapper.Result
import kotlinx.coroutines.flow.firstOrNull

class SearchRepositoryImpl @Inject constructor(
    private val searchAuthorDataSource: SearchAuthorDataSource,
    private val searchComicDataSource: SearchComicDataSource,
    private val searchScanlationGroupDataSource: SearchScanlationGroupDataSource,
    private val tokenRepository: TokenRepository,
    private val searchTagRepository: SearchTagRepository,
    private val userDataSource: SearchUserDataSource
) : SearchRepository{
    override suspend fun searchByTitle(title: String, type: SearchType): Result<List<Any>, DataError.Network> {
        return when(type){
            SearchType.SCANLATION_GROUP -> {
                searchScanlationGroupByTitle(title)
            }
            SearchType.AUTHOR -> {
                searchAuthorByTitle(title)
            }
            SearchType.COMIC -> {
                searchComicByTitle(title)
            }
        }
    }
    private suspend fun searchScanlationGroupByTitle(
        title: String
    ): Result<List<ScanlationGroupSearch>, DataError.Network> {

        return when (val response = searchScanlationGroupDataSource.getScanlationGroupByTitle(title)) {
            is Result.Success -> {
                val scanlationGroups = response.data?.data.orEmpty()

                val leaderIds = scanlationGroups
                    .flatMap { dto ->
                        dto.relationships
                            .filter { it.type == "leader" }
                            .map { it.id }
                    }.distinct()

                val token = tokenRepository.readToken().firstOrNull()
                    ?: return Result.Error(DataError.Network.UNAUTHORIZED)

                when (val leadersResult = userDataSource.getUserSearchById(token, leaderIds)) {
                    is Result.Success -> {
                        val users = leadersResult.data.data
                        val mapped = scanlationGroups.map {
                            ScanlationGrouptoScanlationSearch(it, users)
                        }
                        Result.Success(mapped)
                    }

                    is Result.Error -> Result.Error(leadersResult.error)
                }
            }

            is Result.Error -> Result.Error(response.error)
        }
    }
    private suspend fun searchAuthorByTitle(title: String): Result<List<AuthorSearch>, DataError.Network> {
        return when(val response = searchAuthorDataSource.getAuthorByName(title)){
            is Result.Success ->{
                val scanlationGroups = (response.data.data)
                val rs = scanlationGroups.map {
                    AuthorDTOtoAuthorSearch(it)
                }
                Result.Success(rs)
            }
            is Result.Error -> Result.Error(response.error)
        }
    }
    private suspend fun searchComicByTitle(title: String): Result<List<SearchComic>, DataError.Network> {
        // B1: Gọi API lấy danh sách manga theo title
        return when (val response = searchComicDataSource.getMangaByTitle(title)) {
            is Result.Success -> {
                val mangaDtoList = response.data?.data.orEmpty()
                if (mangaDtoList.isEmpty()) return Result.Success(emptyList())

                val mangaIds = mangaDtoList.map { it.id }
                val authorIds = mangaDtoList
                    .flatMap { it.relationships.filter { it.type == "author" }.map { it.id } }
                    .distinct()
                val coverArtIds = mangaDtoList
                    .flatMap { it.relationships.filter { it.type == "cover_art" }.map { it.id } }
                    .distinct()
                val tagIds = mangaDtoList
                    .flatMap { it.attributes.tags.filter { it.type == "tag" }.map { it.id } }
                    .distinct()

                // B2: Lấy tag
                val tagsResult = searchTagRepository.getTags(tagIds)
                if (tagsResult is Result.Error) return Result.Error(tagsResult.error)
                val tags = (tagsResult as Result.Success).data

                // B3: Gọi 3 API song song
                val authorRes = searchComicDataSource.getAuthorById(authorIds)
                val coverArtRes = searchComicDataSource.getCoverArtById(coverArtIds)
                val statRes = searchComicDataSource.getStatisticsByIds(mangaIds)

                // B4: Kiểm tra kết quả
                if (
                    authorRes is Result.Success &&
                    coverArtRes is Result.Success &&
                    statRes is Result.Success &&
                    statRes.data.statistics != null
                ) {
                    val authorMap = authorRes.data.data.associateBy { it.id }
                    val coverArtMap = coverArtRes.data.data.associateBy { it.id }
                    val statMap = statRes.data.statistics

                    val comics = mangaDtoList.mapNotNull { dto ->
                        val authors = dto.relationships
                            .filter { it.type == "author" }
                            .mapNotNull { authorMap[it.id] }

                        val coverArt = dto.relationships
                            .firstOrNull { it.type == "cover_art" }
                            ?.id
                            ?.let { coverArtMap[it] }

                        val stat = statMap[dto.id]

                        if (coverArt != null && stat != null) {
                            mangaDTOtoMangaSearch(dto, coverArt, authors, stat, tags)
                        } else null
                    }

                    Result.Success(comics)
                } else {
                    // Ưu tiên lỗi có trước
                    listOf(authorRes, coverArtRes, statRes)
                        .firstOrNull()
                        ?.let { return it as Result<List<SearchComic>, DataError.Network> }

                    Result.Error(DataError.Network.UNKNOWN)
                }
            }

            is Result.Error -> Result.Error(response.error)
        }
    }

}