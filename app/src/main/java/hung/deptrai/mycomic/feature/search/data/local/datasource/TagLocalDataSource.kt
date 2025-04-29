package hung.deptrai.mycomic.feature.search.data.local.datasource

import hung.deptrai.mycomic.feature.search.data.dto.tag.TagDTO

interface TagLocalDataSource  {
    suspend fun saveTags(tags: List<TagDTO>)
    suspend fun getTags(): List<TagDTO>
    suspend fun getLastUpdatedTime(): Long
    suspend fun saveLastUpdatedTime(timestamp: Long)
}