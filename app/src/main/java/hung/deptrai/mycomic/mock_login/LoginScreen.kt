package hung.deptrai.mycomic.mock_login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

@Composable
fun LoginScreen(context: Context) {
    val username = "hungStayDeepTry"
    val password = "chuduchung2406"
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        scope.launch {
            isLoading = true
            try {
                // Xây dựng yêu cầu đăng nhập với FormBody
                val formBody = FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .build()

                val request = Request.Builder()
                    .url("https://api.mangadex.org/auth/login")
                    .post(formBody)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .build()

                val client = OkHttpClient()
                val response: Response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    // Lấy chuỗi phản hồi từ body
                    val responseBody = response.body()?.string()

                    if (!responseBody.isNullOrEmpty()) {
                        Log.d("Login", "Đăng nhập thành công: $responseBody")

                        // Giả sử JSON trả về chứa accessToken
                        val token = parseAccessToken(responseBody)
                        token?.let {
                            context.saveAccessToken(it)  // Lưu access token
                            snackbarHostState.showSnackbar("Đăng nhập thành công")
                            Toast.makeText(context, "Access Token: $it", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        snackbarHostState.showSnackbar("Dữ liệu trả về không hợp lệ")
                    }
                } else {
                    // Nếu đăng nhập thất bại
                    val errorBody = response.body()?.string()
                    snackbarHostState.showSnackbar("Đăng nhập thất bại: $errorBody")
                    Log.e("Login", "Đăng nhập thất bại: $errorBody")
                }
            } catch (e: Exception) {
                snackbarHostState.showSnackbar("Lỗi: ${e.message}")
                Log.e("Login", "Lỗi: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    // UI hiển thị trạng thái loading và Snackbar
    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

// Giả lập hàm để phân tích và lấy access token từ response body
fun parseAccessToken(responseBody: String?): String? {
    // Giả sử responseBody chứa JSON và ta cần phân tích nó để lấy accessToken
    // Để đơn giản, ở đây ta sẽ giả định lấy token từ responseBody nếu nó chứa token

    return responseBody?.let {
        // Trích xuất accessToken từ chuỗi JSON
        // Bạn có thể dùng thư viện như Gson hoặc Moshi để phân tích JSON thực tế ở đây
        // Giả sử trong trường hợp này là "session": "abc123"
        // Ví dụ đơn giản:
        if (it.contains("session")) {
            // Tìm kiếm phần tử session trong response và trả về
            it.substringAfter("\"session\":\"").substringBefore("\"")
        } else {
            null
        }
    }
}
