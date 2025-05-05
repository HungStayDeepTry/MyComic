package hung.deptrai.mycomic.feature.search.data.local.datasource

import hung.deptrai.mycomic.core.data.dto.DTOject
import hung.deptrai.mycomic.feature.search.data.dto.tag.TagAttributesDTO
import hung.deptrai.mycomic.feature.search.data.dto.tag.TagDTO

interface TagLocalDataSource  {
    suspend fun saveTags(tags: List<DTOject<TagAttributesDTO>>)
    suspend fun getTags(): List<DTOject<TagAttributesDTO>>
    suspend fun getLastUpdatedTime(): Long
    suspend fun saveLastUpdatedTime(timestamp: Long)
}