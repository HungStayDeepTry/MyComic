package hung.deptrai.mycomic.feature.search.data.remote.dto.author

data class AuthorAttributes(
    val name: String?,
    val imageUrl: String?,
    val biography: Map<String, Any>?,  // Có thể chứa dữ liệu không xác định, cần điều chỉnh theo mục đích sử dụng
    val twitter: String?,
    val pixiv: String?,
    val melonBook: String?,
    val fanBox: String?,
    val booth: String?,
    val namicomi: String?,
    val nicoVideo: String?,
    val skeb: String?,
    val fantia: String?,
    val tumblr: String?,
    val youtube: String?,
    val weibo: String?,
    val naver: String?,
    val website: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val version: Int?
)