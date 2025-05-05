package hung.deptrai.mycomic.feature.search.data.repository

import android.annotation.SuppressLint
import android.util.Log
import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.common.onSuccess
import hung.deptrai.mycomic.core.domain.mapper.TagDTOtoTagEntity
import hung.deptrai.mycomic.core.domain.model.TagEntity
import hung.deptrai.mycomic.feature.search.data.local.datasource.TagLocalDataSource
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchTagDataSource
import hung.deptrai.mycomic.feature.search.domain.repository.SearchTagRepository
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class SearchTagRepositoryImpl @Inject constructor(
    private val searchTagDataSource: SearchTagDataSource,
    private val tagLocalDataSource: TagLocalDataSource
): SearchTagRepository{
    @SuppressLint("NewApi")
    override suspend fun getAllTags(): ResultWrapper<List<TagEntity>> {
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
            return when (val remoteResult = searchTagDataSource.fetchAllTags()) {
                is ResultWrapper.Success -> {
                    remoteResult.onSuccess { response ->
                        try {
                            // Lưu dữ liệu vào local (datastore)
                            tagLocalDataSource.saveTags(response.data)
                            // Lưu thời gian cập nhật cuối cùng
                            tagLocalDataSource.saveLastUpdatedTime(now.toEpochMilli())
                            cachedTagDTOs = tagLocalDataSource.getTags()
                        } catch (e: Exception) {
                            Log.e("Error in Tag Repo", "getAllTags: ${e.message}", )
                            return ResultWrapper.GenericError(error = "Failed to save data locally")
                        }
                    }
                    // Sau khi thành công, trả dữ liệu từ cache
                    ResultWrapper.Success(cachedTagDTOs.map { TagDTOtoTagEntity(it) })
                }
                is ResultWrapper.GenericError -> {
                    // Nếu có lỗi từ API, trả về lỗi GenericError
                    ResultWrapper.GenericError(code = remoteResult.code, error = remoteResult.error)
                }
                is ResultWrapper.NetworkError -> {
                    // Nếu có lỗi mạng, trả về lỗi NetworkError
                    ResultWrapper.NetworkError(exception = remoteResult.exception)
                }
            }
        } else {
            // Nếu dữ liệu cache hợp lệ, trả trực tiếp từ cache
            val tagEntities = cachedTagDTOs.map { TagDTOtoTagEntity(it) }
            ResultWrapper.Success(tagEntities)
        }
    }
}