package hung.deptrai.mycomic.core.domain.model

data class TagEntity(
    val id: String,
    val name: Map<String, String>,
    val group: String,
    val description: Map<String, String> = emptyMap()
)