package hung.deptrai.mycomic.feature.search.domain.model

data class ScanlationGroupSearch(
    val id: String,
    val name: String,
    val leaderName: List<String>?,         // tên leader (nếu lấy được từ relationship)
    val focusedLanguages: List<String>,
    val isOfficial: Boolean,
    val isVerified: Boolean
)