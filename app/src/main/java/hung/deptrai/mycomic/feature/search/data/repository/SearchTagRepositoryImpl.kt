package hung.deptrai.mycomic.feature.search.data.repository

import android.annotation.SuppressLint
import android.util.Log
import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.common.onSuccess
import hung.deptrai.mycomic.core.domain.exception.DataError
//import hung.deptrai.mycomic.core.domain.mapper.TagDTOtoTagEntity
import hung.deptrai.mycomic.core.domain.mapper.TagDTOtoTagSearch
//import hung.deptrai.mycomic.core.domain.model.TagEntity
import hung.deptrai.mycomic.feature.search.data.local.datasource.TagLocalDataSource
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchComicDataSource
//import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchTagDataSource
import hung.deptrai.mycomic.feature.search.domain.model.TagSearch
import hung.deptrai.mycomic.feature.search.domain.repository.SearchTagRepository
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import hung.deptrai.mycomic.core.domain.wrapper.Result

class SearchTagRepositoryImpl @Inject constructor(
    private val searchComicDataSource: SearchComicDataSource,
    private val tagLocalDataSource: TagLocalDataSource
): SearchTagRepository{
    @SuppressLint("NewApi")
    override suspend fun getTags(ids: List<String>): Result<List<TagSearch>, DataError.Network> {
        var cachedTagDTOs = tagLocalDataSource.getTags() // Lấy list<TagDTO> từ local
        val lastFetchedMillis = tagLocalDataSource.getLastUpdatedTime()
        val now = Instant.now()

        // Chuyển lastFetchedMillis thành Instant
        val lastFetched = lastFetchedMillis.let { Instant.ofEpochMilli(it) }

        // Kiểm tra xem cache có hợp lệ không, nếu không hợp lệ thì fetch mới
        val shouldFetch = cachedTagDTOs.isEmpty() || lastFetched == null ||
                ChronoUnit.DAYS.between(lastFetched, now) > 2

        return if (shouldFetch) {
            // Fetch dữ liệu từ API
            // Kiểm tra kết quả từ remote
            return when (val remoteResult = searchComicDataSource.fetchAllTags()) {
                is Result.Success -> {
//                    remoteResult.onSuccess { response ->
                        try {
                            // Lưu dữ liệu vào local (datastore)
                            tagLocalDataSource.saveTags(remoteResult.data.data)
                            // Lưu thời gian cập nhật cuối cùng
                            tagLocalDataSource.saveLastUpdatedTime(now.toEpochMilli())
                            cachedTagDTOs = tagLocalDataSource.getTagsByIds(ids)
                        } catch (e: Exception) {
                            Log.e("Error in Tag Repo", "getAllTags: ${e.message}", )
//                            return Result.Error(remoteResult.)
                        }
//                    }
                    // Sau khi thành công, trả dữ liệu từ cache
                    Result.Success(cachedTagDTOs.map { TagDTOtoTagSearch(it) })
                }
                is Result.Error -> {
                    // Nếu có lỗi từ API, trả về lỗi GenericError
                    Result.Error(remoteResult.error)
                }
            }
        } else {
            // Nếu dữ liệu cache hợp lệ, trả trực tiếp từ cache
            val tagEntities = cachedTagDTOs.map { TagDTOtoTagSearch(it) }
            Result.Success(tagEntities)
        }
    }
}