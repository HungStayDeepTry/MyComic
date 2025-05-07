package hung.deptrai.mycomic.core.common

import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.wrapper.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T, DataError.Network> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall()

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error(DataError.Network.UNKNOWN) // Body rỗng
            } else {
                Result.Error(mapHttpCodeToNetworkError(response.code()))
            }
        } catch (e: IOException) {
            Result.Error(DataError.Network.NO_INTERNET)
        } catch (e: HttpException) {
            Result.Error(mapHttpCodeToNetworkError(e.code()))
        } catch (e: Exception) {
            Result.Error(DataError.Network.UNKNOWN)
        }
    }
}
fun mapHttpCodeToNetworkError(code: Int): DataError.Network = when (code) {
    400 -> DataError.Network.BAD_REQUEST
    401 -> DataError.Network.UNAUTHORIZED
    403 -> DataError.Network.FORBIDDEN
    404 -> DataError.Network.NOT_FOUND
    405 -> DataError.Network.METHOD_NOT_ALLOWED
    408 -> DataError.Network.REQUEST_TIMEOUT
    409 -> DataError.Network.CONFLICT
    410 -> DataError.Network.GONE
    411 -> DataError.Network.LENGTH_REQUIRED
    413 -> DataError.Network.PAYLOAD_TOO_LARGE
    415 -> DataError.Network.UNSUPPORTED_MEDIA_TYPE
    429 -> DataError.Network.TOO_MANY_REQUESTS
    500 -> DataError.Network.SERVER_ERROR
    502 -> DataError.Network.BAD_GATEWAY
    503 -> DataError.Network.SERVICE_UNAVAILABLE
    504 -> DataError.Network.GATEWAY_TIMEOUT
    else -> DataError.Network.UNKNOWN
}