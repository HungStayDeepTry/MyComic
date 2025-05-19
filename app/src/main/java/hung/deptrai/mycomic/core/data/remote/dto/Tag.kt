package hung.deptrai.mycomic.core.data.remote.dto

data class Tag(
    val attributes: hung.deptrai.mycomic.core.data.remote.dto.AttributesX,
    val id: String,
    val relationships: List<Any?>,
    val type: String
)