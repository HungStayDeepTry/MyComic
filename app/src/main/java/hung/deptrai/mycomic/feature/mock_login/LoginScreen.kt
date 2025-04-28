package hung.deptrai.mycomic.feature.mock_login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import hung.deptrai.mycomic.core.data.local.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

@Composable
fun LoginScreen(context: Context ,navController: NavController) {
    val username = "hungStayDeepTry"
    val password = "chuduchung2406"
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var isLoading by remember { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text("Login Screen")
    }
    LaunchedEffect(Unit) {
        scope.launch {
            isLoading = true
            try {
                val responseBody = withContext(Dispatchers.IO) {
                    val formBody = FormBody.Builder()
                        .add("username", username)
                        .add("password", password)
                        .add("grant_type", "password") // Thêm các tham số cần thiết khác
                        .add("client_id", "personal-client-0c63771e-b730-4478-95bd-5f466128d73e-4f110401") // Ví dụ client_id nếu có
                        .add("client_secret", "YZUGV0KFRqbp9C3WBxdIcMPs5NvbcJh1") // Ví dụ client_secret nếu có
                        .build()

                    val request = Request.Builder()
                        .url("https://auth.mangadex.org/realms/mangadex/protocol/openid-connect/token")
                        .post(formBody) // Sử dụng phương thức POST với body dạng FormBody
                        .header("Content-Type", "application/x-www-form-urlencoded") // Đặt header đúng kiểu
                        .build()


                    val client = OkHttpClient()
                    val response: Response = client.newCall(request).execute()

                    if (response.isSuccessful) {
                        response.body?.string()
                    } else {
                        throw Exception("Đăng nhập thất bại: ${response.body?.string()}")
                    }
                }

                if (!responseBody.isNullOrEmpty()) {
                    Log.d("Login", "Đăng nhập thành công: $responseBody")

                    val token = parseAccessToken(responseBody)
                    token?.let {
//                        context.saveAccessToken(it)
                        TokenManager.saveToken(context, token)
                        snackbarHostState.showSnackbar("Đăng nhập thành công")
                        Toast.makeText(context, "Access Token: $it", Toast.LENGTH_LONG).show()
                        navController.navigate("search")
                    }
                } else {
                    snackbarHostState.showSnackbar("Dữ liệu trả về không hợp lệ")
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
    return try {
        val tokenResponse = responseBody?.let { Json.decodeFromString<TokenResponse>(it) }
        tokenResponse?.access_token
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
@Serializable
data class TokenResponse(
    val access_token: String,
    val expires_in: Int,
    val refresh_expires_in: Int,
    val refresh_token: String,
    val token_type: String,
    @SerialName("not-before-policy") val notBeforePolicy: Int? = null,  // Giữ lại trường này để ánh xạ với JSON
    @SerialName("session_state") val sessionState : String? = null,
    val scope: String,
    val client_type: String
)
