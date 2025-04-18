package hung.deptrai.mycomic.mock_login

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = "token_prefs")

suspend fun Context.saveAccessToken(token: String) {
    tokenDataStore.edit {
        it[stringPreferencesKey("access_token")] = token
    }
}

fun Context.getAccessToken(): Flow<String?> {
    return tokenDataStore.data.map {
        it[stringPreferencesKey("access_token")]
    }
}
