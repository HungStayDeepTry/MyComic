package hung.deptrai.mycomic.core.domain.model

import hung.deptrai.mycomic.feature.search.data.remote.dto.Relationship

data class AuthorEntity(
    val id: String,
    val name: String,
    val biography: Map<String, String>?,
    val website: String?,
    val twitter: String?,
    val pixiv: String?,
    val fanBox: String?,
    val booth: String?,
    val tumblr: String?,
    val youtube: String?,
    val weibo: String?,
    val naver: String?,
    val imageUrl: String?, // ✅ thêm dòng này
    val createdAt: String,
    val updatedAt: String,
    val version: Int,
    val relationships: List<Relationship>
)
