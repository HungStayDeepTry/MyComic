package hung.deptrai.mycomic.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.encryptedDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "secure_prefs",
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, "secure_prefs"))
    }
)