package hung.deptrai.mycomic.core.data.local

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object TokenManager {

    suspend fun saveToken(context: Context, token: String) {
        context.tokenDataStore.updateData { token }
    }

    fun readToken(context: Context): Flow<String> {
        return context.tokenDataStore.data.map { it }
    }
}