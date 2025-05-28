package hung.deptrai.mycomic.core.data.remote.dto.wrapper

data class JsonFewestResponse<T> (
    val result: String,
    val data: T
)