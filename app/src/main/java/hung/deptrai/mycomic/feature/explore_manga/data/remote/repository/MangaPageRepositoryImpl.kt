package hung.deptrai.mycomic.feature.explore_manga.data.remote.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import hung.deptrai.constants.MdConstants
import hung.deptrai.core.network.ProxyRetrofitQueryMap
import hung.deptrai.mycomic.core.data.remote.datasource.AuthorDataSource
import hung.deptrai.mycomic.core.data.remote.datasource.ChapterDataSource
import hung.deptrai.mycomic.core.data.remote.datasource.ScanlationGroupDataSource
import hung.deptrai.mycomic.core.data.remote.datasource.StatisticDataSource
import hung.deptrai.mycomic.core.data.remote.dto.IncludesAttributesDto
import hung.deptrai.mycomic.core.data.utils.MdUtil
import hung.deptrai.mycomic.core.data.utils.chapterDTOtoChapterEntity
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.mapper.chapterEntitytoChapterHome
import hung.deptrai.mycomic.core.domain.mapper.mangaDTOtoMangaEntity
import hung.deptrai.mycomic.core.domain.model.Tag
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.feature.explore_manga.data.local.HomeLocalDataSource
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.ChapterEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.CustomType
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.HomeMangaEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.MangaTagCrossRef
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.TagEntity
import hung.deptrai.mycomic.feature.explore_manga.data.remote.datasource.MangaPageDataSource
import hung.deptrai.mycomic.feature.explore_manga.domain.MangaHome
import hung.deptrai.mycomic.feature.explore_manga.domain.MangaPageRepository
import hung.deptrai.mycomic.feature.explore_manga.domain.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MangaPageRepositoryImpl @Inject constructor(
    private val dataSource: MangaPageDataSource,
    private val statisticDs: StatisticDataSource,
    private val authorDataSource: AuthorDataSource,
    private val chapterDataSource: ChapterDataSource,
    private val scanlationGroupDataSource: ScanlationGroupDataSource,
    private val localDataSource: HomeLocalDataSource
) : MangaPageRepository{
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun fetchMangaPageInfo(isRefresh: Boolean): Flow<List<MangaHome>> {
        return combine(
            getLatestChapters(isRefresh),
            getPopularNewTitles(isRefresh)
        ) { latestList, popularList ->
            // Gán type trước khi gộp
            val latest = latestList.map { it.copy(customType = Type.LATEST_UPDATES) }
            val popular = popularList.map { it.copy(customType = Type.POPULAR_NEW_TITLES) }

            latest + popular // Gộp lại thành 1 list
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun recentlyAdded(page: Int): Result<List<Pair<HomeMangaEntity, List<TagEntity>>>, DataError.Network>    {
        return withContext(Dispatchers.IO) {
            val queryParameters = mutableMapOf<String, Any>()
            queryParameters[MdConstants.SearchParameters.limit] = MdConstants.Limits.manga
            queryParameters[MdConstants.SearchParameters.offset] = MdUtil.getMangaListOffset(page)

            when (val rs = dataSource.recentlyAdded(ProxyRetrofitQueryMap(queryParameters))) {
                is Result.Success -> {
                    val res = rs.data.data
                    if (res.isEmpty()) return@withContext Result.Success(emptyList())

                    val coverArtMap = res.associate { dto ->
                        val coverArt = dto.relationships.firstOrNull {
                            it.type == "cover_art" && it.attributes != null
                        }?.attributes
                        dto.id to coverArt
                    }

                    val mangaIds = res.map { it.id }
                    when (val statRes = statisticDs.getStatisticsForMangaByIds(mangaIds)) {
                        is Result.Success -> {
                            val data = res.map {
                                mangaDTOtoMangaEntity(
                                    mangaDTO = it,
                                    coverArtDTO = coverArtMap[it.id],
                                    customType = 6
                                )
                            }
                            Result.Success(data)
                        }

                        is Result.Error -> Result.Error(statRes.error)
                    }
                }

                is Result.Error -> Result.Error(rs.error)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun popularNewTitles(page: Int): Result<List<Pair<HomeMangaEntity, List<TagEntity>>>, DataError.Network>{
        return withContext(Dispatchers.IO){
            val queryParameters = mutableMapOf<String, Any>()

            queryParameters[MdConstants.SearchParameters.limit] = 10
            queryParameters[MdConstants.SearchParameters.offset] = MdUtil.getMangaListOffset(page)

            // Thêm các include cần thiết
            queryParameters["includes[]"] = listOf("cover_art", "artist", "author")

            // Sắp xếp theo lượt theo dõi giảm dần
            queryParameters["order[followedCount]"] = "desc"

            // Các mức độ nội dung
            queryParameters["contentRating[]"] = listOf("safe", "suggestive", "erotica", "pornographic")

            // Chỉ lấy manga có chương
            queryParameters["hasAvailableChapters"] = true

            // createdAtSince = ngày hiện tại trừ đi 30 ngày, định dạng ISO 8601
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val oneMonthAgo = OffsetDateTime.now(ZoneOffset.UTC).minusDays(30)
            queryParameters["createdAtSince"] = oneMonthAgo.format(formatter)

            when (val rs = dataSource.popularNewTitles(ProxyRetrofitQueryMap(queryParameters))){
                is Result.Success -> {
                    val res = rs.data.data
                    if (res.isEmpty()) return@withContext Result.Success(emptyList())

                    val coverArtMap = res.associate { dto ->
                        val coverArt = dto.relationships.firstOrNull {
                            it.type == "cover_art" && it.attributes != null
                        }?.attributes
                        dto.id to coverArt
                    }

                    val mangaIds = res.map { it.id }

                    val authorIds = res
                        .flatMap { it.relationships.filter { it.type == "author" }.map { it.id } }
                        .distinct()

                    val artistIds = res
                        .flatMap { it.relationships.filter { it.type == "artist" }.map { it.id } }
                        .distinct()

                    val author = authorDataSource.getAuthorById(authorIds)
                    val artist = authorDataSource.getAuthorById(artistIds)

                    if(
                        author is Result.Success &&
                        artist is Result.Success
                    ){
                        val authorMap = author.data.data.associateBy { it.id }
                        val artistMap = artist.data.data.associateBy { it.id }

                        val comics = res.mapNotNull { dto ->
                            val authors = dto.relationships
                                .filter { it.type == "author" }
                                .mapNotNull { authorMap[it.id] }

                            val coverArt = coverArtMap[dto.id]

                            val artists = dto.relationships
                                .filter {
                                    it.type == "artist"
                                }
                                .mapNotNull {
                                    artistMap[it.id]
                                }

                            if (coverArt != null) {
                                mangaDTOtoMangaEntity(dto, coverArt, authors, artists, customType = 0)
                            } else null
                        }
                        Result.Success(comics)
                    }
                    else{
                        // Ưu tiên lỗi có trước
                        listOf(author, artist)
                            .firstOrNull()
                            ?.let { return@let it as Result<List<Pair<MangaHome, TagEntity>>, DataError.Network> }

                        Result.Error(DataError.Network.UNKNOWN)
                    }
                }
                is Result.Error -> {
                    Result.Error(rs.error)
                }
            }
        }
    }

    private suspend fun getLatestChapterUpdatedFromRemote():
            Result<List<ChapterEntity>, DataError.Network> {
        return withContext(Dispatchers.IO) {
            when (val res = chapterDataSource.getLatestUpdatedChapter()) {
                is Result.Success -> {
                    val rs = res.data.data
                    val chapterIds = rs.map { it.id }
                    val scanlationGroupIds = rs.mapNotNull {
                        it.relationships.firstOrNull{
                            it.type == "scanlation_group"
                        }?.id
                    }
                    val statisticRes = statisticDs.getStatisticsForChapterByIds(chapterIds)
                    val scanGroupRes = scanlationGroupDataSource.getScanlationGroup(scanlationGroupIds)
                    if(statisticRes is Result.Success && scanGroupRes is Result.Success) {
                            val statMap = statisticRes.data.statistics
                            val scanMap = scanGroupRes.data.data.associateBy { it.id }
                            val chapters = rs.mapNotNull { dto ->
                                val scanGroups = dto.relationships
                                    .firstOrNull { it.type == "scanlation_group" }
                                    ?.id
                                    ?.let { scanMap[it] }
                                val statistic = statMap[dto.id]
                                if(statistic != null){
                                    chapterDTOtoChapterEntity(dto, statistic, scanGroups)
                                } else{
                                    null
                                }
                            }
                            Result.Success(chapters)
                    } else{
                        listOf(scanGroupRes, scanGroupRes)
                            .firstOrNull()
                            ?.let { return@let it as Result<List<MangaHome>, DataError.Network> }

                        Result.Error(DataError.Network.UNKNOWN)
                    }
                }
                is Result.Error -> Result.Error(res.error)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getLatestChapterWithManga(mangaIds: List<String>): Result<List<HomeMangaEntity>, DataError.Network>{
        return withContext(Dispatchers.IO) {
            when (val res = dataSource.getMangaByIds(mangaIds)) {
                is Result.Success -> {
                    val coverArtMap = res.data.data.associate { dto ->
                        val coverArt = dto.relationships.firstOrNull {
                            it.type == "cover_art" && it.attributes != null
                        }?.attributes
                        dto.id to coverArt
                    }
                    val data = res.data.data.map { it ->
                        val coverArt: IncludesAttributesDto? = coverArtMap[it.id]
                        mangaDTOtoMangaEntity(it, customType = 1, coverArtDTO = coverArt).first
                    }
                    Result.Success(data)
                }

                is Result.Error -> {
                    Result.Error(res.error)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun refreshLatestChapters() {
        when (val result = getLatestChapterUpdatedFromRemote()) {
            is Result.Success -> {
                val rs = result.data.map { it.mangaId }
                val mangaRes = getLatestChapterWithManga(rs)

                if (mangaRes is Result.Success) {
                    try {
                        withContext(Dispatchers.IO) {
                            localDataSource.upsertAllMangas(
                                mangaRes.data,
                                customType = CustomType.LATEST_UPDATES
                            )
                            localDataSource.upsertChapter(result.data)
                        }
                    } catch (e: Exception) {
                        // Ghi log hoặc xử lý lỗi cụ thể
                        Log.e("refreshLatestChapters", "Lỗi khi insert vào Room: ${e.message}", e)
                    }
                }
            }

            is Result.Error -> {
                // Tùy chọn xử lý lỗi từ API
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getLatestChapters(isRefresh: Boolean): Flow<List<MangaHome>> = flow {
        if (isRefresh) {
            refreshLatestChapters()
        }
        emitAll(getMangaByLatestChaptersFromLocal())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPopularNewTitles(isRefresh: Boolean): Flow<List<MangaHome>> = flow{
        if(isRefresh){
            refreshPopularNewTitles()
        }
        emitAll(getPopularNewTitlesFromLocal())
    }


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun refreshPopularNewTitles(){
        when(val result = popularNewTitles(1)){
            is Result.Success -> {
                val mangaList = result.data.map { it.first }
                val tagList = result.data.flatMap { it.second }.distinctBy { it.id }
                val crossRefs = result.data.flatMap { (manga, tags) ->
                    tags.map { tag ->
                        MangaTagCrossRef(
                            mangaId = manga.id,
                            tagId = tag.id
                        )
                    }
                }

                // Insert mangas
                localDataSource.upsertAllMangas(mangaList, CustomType.POPULAR_NEW_TITLES)

                // Insert tags
                localDataSource.insertTags(tagList)

                // Insert cross references
                localDataSource.insertMangaTagCrossRefs(crossRefs, mangaList.map { it.id })
            }

            is Result.Error -> {
                // Handle error if needed
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getMangaByLatestChaptersFromLocal(): Flow<List<MangaHome>> {
        return localDataSource.getLatestChapters()
            .flatMapLatest { chapters ->
                val mangaIds = chapters.map { it.mangaId }.distinct()

                combine(
                    localDataSource.getMangaByIds(mangaIds),
                    localDataSource.getTagsByMangaIds(mangaIds)
                ) { mangas, tagsByMangaId ->

                    // Bước 1: Lấy chương mới nhất cho mỗi manga
                    val latestChapterByManga = chapters
                        .groupBy { it.mangaId }
                        .mapNotNull { (_, chapterList) ->
                            chapterList.maxByOrNull { it.updatedAt }
                        }

                    // Bước 2: Sắp xếp theo updatedAt giảm dần
                    val sortedChapters = latestChapterByManga
                        .sortedByDescending { it.updatedAt }

                    // Bước 3: Map theo đúng thứ tự
                    sortedChapters.mapNotNull { chapter ->
                        val manga = mangas.firstOrNull { it.id == chapter.mangaId } ?: return@mapNotNull null
                        val tags = tagsByMangaId[manga.id] ?: emptyList()

                        MangaHome(
                            id = manga.id,
                            title = manga.title,
                            authorName = manga.authorName,
                            artist = manga.artist,
                            coverArt = manga.coverArtLink.orEmpty(),
                            originalLang = manga.originalLang,
                            lastUpdatedChapter = chapterEntitytoChapterHome(chapter),
                            tags = tags.map { Tag(it.id, it.name, it.group) },
                            customType = Type.LATEST_UPDATES
                        )
                    }
                }
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPopularNewTitlesFromLocal(): Flow<List<MangaHome>>{
        return localDataSource.getPopularNewTitles()
            .flatMapLatest { mangas ->
                val mangaIds = mangas.map { it.id }
                localDataSource.getTagsByMangaIds(mangaIds)
                    .map { tagsByMangaId ->
                        mangas.map { manga ->
                            MangaHome(
                                id = manga.id,
                                title = manga.title,
                                authorName = manga.authorName,
                                artist = manga.artist,
                                coverArt = manga.coverArtLink?:"",
                                originalLang = manga.originalLang,
                                lastUpdatedChapter = null,
                                tags = tagsByMangaId[manga.id]?.map { tagEntity ->
                                    Tag(tagEntity.id, tagEntity.name, tagEntity.group)
                                } ?: emptyList(),
                                customType = Type.POPULAR_NEW_TITLES
                            )
                        }
                    }
            }
    }


//    override fun observeMangaByType(type: CustomType): Flow<List<HomeMangaEntity>>{
//        return when(type){
//            CustomType.POPULAR_NEW_TITLES -> localDataSource.getPopularNewTitles()
//            CustomType.RECENTLY_ADDED -> localDataSource.getRecentlyAdded(Int.MAX_VALUE)
//            CustomType.STAFF_PICKS -> localDataSource.getStaffPicks(Int.MAX_VALUE)
//            CustomType.SELF_PUBLISHED -> localDataSource.getSelfPublished(Int.MAX_VALUE)
//            CustomType.FEATURE -> localDataSource.getFeature(Int.MAX_VALUE)
//            CustomType.SEASONAL -> localDataSource.getSeasonal(Int.MAX_VALUE)
//            CustomType.LATEST_UPDATES -> localDataSource.getMangaByIds()
//        }
//    }
}