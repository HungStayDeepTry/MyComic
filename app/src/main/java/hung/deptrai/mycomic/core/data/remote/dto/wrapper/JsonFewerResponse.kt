package hung.deptrai.mycomic.core.data.remote.dto.wrapper

data class JsonFewerResponse<T> (
    val result: String,
    val data: List<T>
)