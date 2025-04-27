package hung.deptrai.mycomic.core.data.dto

data class JsonFewestResponse<T> (
    val result: String,
    val data: T
)