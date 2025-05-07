package hung.deptrai.mycomic.core.data.dto.tag

import kotlinx.serialization.Serializable

@Serializable
data class TagAttributesDTO(
    val name: Map<String, String>, // key: "en", "jp", etc.
    val description: Map<String, String>? = emptyMap(), // có thể rỗng
    val group: String,
    val version: Int
)
