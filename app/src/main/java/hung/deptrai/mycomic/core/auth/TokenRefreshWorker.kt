package hung.deptrai.mycomic.core.auth

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import hung.deptrai.mycomic.feature.mock_login.TokenResponse
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit

class TokenRefreshWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val token = TokenManager.readToken(applicationContext).first()

        val currentTime = System.currentTimeMillis()
        val timeRemaining = token.expiresAt - currentTime

        if (timeRemaining <= 60_000) {
            try {
                val response: TokenResponse = LoginApi.refreshToken(token.refreshToken)
                TokenManager.saveToken(applicationContext, response)

                // 🔁 Lên lịch lại worker cho lần kế tiếp sau khi refresh thành công
                schedule(applicationContext, response.expires_in * 1000L - 60_000L)

                return Result.success()
            } catch (e: Exception) {
                e.printStackTrace()
                return Result.retry()
            }
        } else {
            // 🔁 Nếu chưa đến lúc, lên lịch lại đúng thời điểm cần kiểm tra tiếp
            schedule(applicationContext, timeRemaining - 60_000L)
        }

        return Result.success()
    }

    companion object {
        private const val WORK_NAME = "TokenRefreshWorker"

        fun schedule(context: Context, delayMillis: Long) {
            val work = OneTimeWorkRequestBuilder<TokenRefreshWorker>()
                .setInitialDelay(delayMillis.coerceAtLeast(0L), TimeUnit.MILLISECONDS)
                .build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                work
            )
        }
    }
}