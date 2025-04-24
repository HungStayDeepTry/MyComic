package hung.deptrai.mycomic.core.common

import retrofit2.Response
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ResultWrapper<T> {
    return try {
        val response = apiCall()

        // Kiểm tra mã trạng thái HTTP
        if (response.isSuccessful) {
            // Thành công, trả về dữ liệu nếu có
            response.body()?.let {
                ResultWrapper.Success(it)
            } ?: ResultWrapper.GenericError(
                code = response.code(),
                error = "Empty response body"
            )
        } else {
            // Lỗi, trả về mã lỗi và thông báo lỗi chi tiết
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            ResultWrapper.GenericError(
                code = response.code(),
                error = errorBody
            )
        }
    } catch (e: IOException) {
        // Lỗi mạng
        ResultWrapper.NetworkError(e)
    } catch (e: Exception) {
        // Lỗi chung
        ResultWrapper.GenericError(error = e.localizedMessage)
    }
}