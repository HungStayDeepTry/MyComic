package hung.deptrai.mycomic.core.data.remote.dto.artist

data class ArtistAttributes (
    val name: String,
    val imageUrl: String,
    val biography: Map<String, String>?,
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
    val version : Int
)