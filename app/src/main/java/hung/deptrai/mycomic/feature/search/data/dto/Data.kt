package hung.deptrai.mycomic.feature.search.data.dto

data class Data(
    val attributes: hung.deptrai.mycomic.feature.search.data.dto.Attributes,
    val id: String,
    val relationships: List<hung.deptrai.mycomic.feature.search.data.dto.Relationship>,
    val type: String
)