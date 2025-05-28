package hung.deptrai.mycomic.core.data.remote.dto.wrapper

data class JsonResponse<T>(
    val result: String,
    val response: String,
    val data: List<T>,
    val limit: Int,
    val offset: Int,
    val total: Int
)
