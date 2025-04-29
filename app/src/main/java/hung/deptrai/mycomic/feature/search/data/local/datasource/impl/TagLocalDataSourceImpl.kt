package hung.deptrai.mycomic.feature.search.data.local.datasource.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import hung.deptrai.mycomic.feature.search.data.dto.tag.TagDTO
import hung.deptrai.mycomic.feature.search.data.local.datasource.TagLocalDataSource
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TagLocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : TagLocalDataSource{

    private val tagKey = stringPreferencesKey("tag_json")
    private val updatedKey = longPreferencesKey("tags_updated_at")

    override suspend fun saveTags(tags: List<TagDTO>) {
        val json = Json.encodeToString(tags)
        dataStore.edit {
            it[tagKey] = json
            it[updatedKey] = System.currentTimeMillis()
        }
    }

    override suspend fun getTags(): List<TagDTO> {
        val prefs = dataStore.data.first()
        val json = prefs[tagKey] ?: return emptyList()
        return Json.decodeFromString(json)
    }

    override suspend fun getLastUpdatedTime(): Long {
        return dataStore.data.first()[updatedKey] ?: 0L
    }

    override suspend fun saveLastUpdatedTime(timestamp: Long) {
        dataStore.edit {
            it[updatedKey] = timestamp
        }
    }
}