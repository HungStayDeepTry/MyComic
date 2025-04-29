package hung.deptrai.mycomic.feature.search.data.dto

data class Relationship(
    val id: String,
    val related: String?=null,
    val type: String
)