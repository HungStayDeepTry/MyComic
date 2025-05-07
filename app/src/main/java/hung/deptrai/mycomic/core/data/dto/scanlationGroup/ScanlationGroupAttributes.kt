package hung.deptrai.mycomic.core.data.dto.scanlationGroup

data class ScanlationGroupAttributes(
    val name: String,
    val altNames: List<Map<String, String>>,
    val locked: Boolean,
    val website: String?,
    val ircServer: String?,
    val ircChannel: String?,
    val discord: String?,
    val contactEmail: String?,
    val description: Any?, // Có thể là String, null hoặc {} nên nên để Any
    val twitter: String?,
    val mangaUpdates: String?,
    val focusedLanguages: List<String>,
    val official: Boolean,
    val verified: Boolean,
    val inactive: Boolean,
    val publishDelay: String?,
    val exLicensed: Boolean? = null,
    val createdAt: String,
    val updatedAt: String,
    val version: Int
)
