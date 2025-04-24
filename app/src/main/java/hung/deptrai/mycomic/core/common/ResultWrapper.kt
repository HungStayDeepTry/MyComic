package hung.deptrai.mycomic.core.common

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: String? = null) : ResultWrapper<Nothing>()
    data class NetworkError(val exception: Exception? = null) : ResultWrapper<Nothing>()
}
inline fun <T> ResultWrapper<T>.onSuccess(action: (T) -> Unit): ResultWrapper<T> {
    if (this is ResultWrapper.Success) action(data)
    return this
}

inline fun <T> ResultWrapper<T>.onError(action: (String) -> Unit): ResultWrapper<T> {
    when (this) {
        is ResultWrapper.GenericError -> action(error ?: "Unknown Error")
        is ResultWrapper.NetworkError -> action(exception?.message ?: "Network Error")
        else -> {}
    }
    return this
}