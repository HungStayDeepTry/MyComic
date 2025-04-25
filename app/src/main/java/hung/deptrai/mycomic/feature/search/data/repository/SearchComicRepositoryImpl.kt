package hung.deptrai.mycomic.feature.search.data.repository

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.domain.mapper.mangaDTOtoMangaEntity
import hung.deptrai.mycomic.core.domain.model.MangaEntity
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchComicDataSource
import hung.deptrai.mycomic.feature.search.domain.repository.SearchComicRepository
import javax.inject.Inject

class SearchComicRepositoryImpl @Inject constructor(
    private val searchMangaDataSource: SearchComicDataSource
) : SearchComicRepository {

    override suspend fun searchComicByTitle(title: String): ResultWrapper<List<MangaEntity>> {
        // 1. Gọi API để tìm manga theo title
        val response = searchMangaDataSource.getMangaByTitle(title)

        // 2. Xử lý theo ResultWrapper
        return when (response) {
            is ResultWrapper.Success -> {
                val mangaDtoList = response.data?.data ?: return ResultWrapper.Success(emptyList()) // Kiểm tra danh sách manga trả về rỗng

                val mangaIds = mangaDtoList.map { it.id }
                val authorIds = mangaDtoList.flatMap { it.relationships.filter { it.type == "author" }.map { it.id } }.distinct()
                val coverArtIds = mangaDtoList.flatMap { it.relationships.filter { it.type == "cover_art" }.map { it.id } }.distinct()

                // 3. Gọi các API khác song song (optional)
                val authorRes = searchMangaDataSource.getAuthorById(authorIds)
                val coverArtRes = searchMangaDataSource.getCoverArtById(coverArtIds)
                val statRes = searchMangaDataSource.getStatisticsByIds(mangaIds)

                // 4. Kiểm tra tất cả response có phải là Success không
                if (
                    authorRes is ResultWrapper.Success &&
                    coverArtRes is ResultWrapper.Success &&
                    statRes is ResultWrapper.Success &&
                    statRes.data?.statistics != null
                ) {
                    val authorMap = authorRes.data?.data?.associateBy { it.id } ?: emptyMap()
                    val coverArtMap = coverArtRes.data?.data?.associateBy { it.id } ?: emptyMap()
                    val statMap = statRes.data.statistics

                    val result = mangaDtoList.mapNotNull { dto ->
                        val mangaId = dto.id
                        val authorIdsForManga = dto.relationships.filter { it.type == "author" }.map { it.id }
                        val authors = authorIdsForManga.mapNotNull { authorMap[it] }

                        val coverArtId = dto.relationships.firstOrNull { it.type == "cover_art" }?.id
                        val coverArt = coverArtId?.let { coverArtMap[it] }

                        val statistic = statMap[mangaId]

                        if (coverArt != null && statistic != null) {
                            mangaDTOtoMangaEntity(dto, coverArt, authors, statistic)
                        } else null
                    }

                    ResultWrapper.Success(result)
                } else {
                    // Sử dụng thông tin lỗi trả về từ các API
                    val error = authorRes.takeIf { it is ResultWrapper.GenericError }
                        ?: coverArtRes.takeIf { it is ResultWrapper.GenericError }
                        ?: statRes.takeIf { it is ResultWrapper.GenericError }

                    error?.let {
                        // Trả về lỗi từ API nếu có
                        return it as ResultWrapper.GenericError
                    } ?: ResultWrapper.GenericError(code = 500, error = "Failed to fetch related data")
                }
            }

            is ResultWrapper.GenericError -> response
            is ResultWrapper.NetworkError -> response
        }
    }
}
