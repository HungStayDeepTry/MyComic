//package hung.deptrai.mycomic.feature.search.domain.usecase
//
//import hung.deptrai.mycomic.core.common.ResultWrapper
//import hung.deptrai.mycomic.core.domain.model.ScanlationGroupEntity
//import hung.deptrai.mycomic.feature.search.domain.repository.SearchScanlationGroupRepository
//import hung.deptrai.mycomic.feature.search.domain.model.ScanlationGroupSearch
//import javax.inject.Inject
//
//class SearchScanlationGroupUseCase @Inject constructor(
//    private val searchScanlationGroupRepository: SearchScanlationGroupRepository
//) {
//    suspend fun searchScanlationGroupByTitle(title: String): ResultWrapper<List<ScanlationGroupSearch>>{
//        return when (val resultWrapper = searchScanlationGroupRepository.searchScanlationGroupByTitle(title)) {
//            is ResultWrapper.Success -> {
//                // Chuyển đổi từ MangaEntity sang SearchComicModel
//                val searchComics = mapToScanlationSearch(resultWrapper.data)
////                ResultWrapper.Success(searchComics.ifEmpty { emptyList() }) // Đảm bảo trả về danh sách không phải null
//                ResultWrapper.Success(searchComics)
//            }
//            is ResultWrapper.GenericError -> {
//                // Trả về lỗi chi tiết cho UI
//                ResultWrapper.GenericError(
//                    code = resultWrapper.code,
//                    error = "Error: ${resultWrapper.error}"
//                )
//            }
//            is ResultWrapper.NetworkError -> {
//                // Lỗi mạng, có thể cung cấp thêm thông báo lỗi chi tiết
//                ResultWrapper.NetworkError(
//                    exception = resultWrapper.exception
//                )
//            }
//        }
//    }
//    private fun mapToScanlationSearch(scanlationGroupEntities: List<ScanlationGroupEntity>): List<ScanlationGroupSearch> {
//        val searchComics = scanlationGroupEntities.map { entity ->
//
//            val leaderIDs = entity.relationships.filter {
//                it.type == "leader"
//            }.map {
//                it.id
//            }
//
//            ScanlationGroupSearch(
//                id = entity.id,
//                name = entity.name,
//                leaderName = leaderIDs,
//                focusedLanguages = entity.focusedLanguages,
//                isOfficial = entity.official,
//                isVerified = entity.verified
//            )
//        }
//        // Trả về danh sách dưới dạng một biến duy nhất
//        return searchComics
//    }
//}