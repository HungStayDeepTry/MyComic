package hung.deptrai.mycomic.feature.search.domain.usecase

import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.core.domain.model.TagEntity
import hung.deptrai.mycomic.feature.search.domain.repository.SearchTagRepository
import hung.deptrai.mycomic.feature.search.domain.model.TagSearch
import javax.inject.Inject

class SearchTagUseCase @Inject constructor(
    private val searchTagRepository: SearchTagRepository
){
    suspend fun takeAllTags(): ResultWrapper<List<TagSearch>>{
        return when(val res = searchTagRepository.getAllTags()){
            is ResultWrapper.Success -> {
                // Chuyển đổi từ MangaEntity sang SearchComicModel
                val searchTag = mapToTagDTO(res.data)
                ResultWrapper.Success(searchTag)
            }
            is ResultWrapper.GenericError -> {
                // Trả về lỗi chi tiết cho UI
                ResultWrapper.GenericError(
                    code = res.code,
                    error = "Error: ${res.error}"
                )
            }
            is ResultWrapper.NetworkError -> {
                // Lỗi mạng, có thể cung cấp thêm thông báo lỗi chi tiết
                ResultWrapper.NetworkError(
                    exception = res.exception
                )
            }
        }
    }

    private fun mapToTagDTO(tagEntities : List<TagEntity>): List<TagSearch>{
        val searchTags = tagEntities.map { en ->
            TagSearch(
                id = en.id,
                name = en.name["en"].toString(),
                group = en.group
            )
        }
        return searchTags
    }
}