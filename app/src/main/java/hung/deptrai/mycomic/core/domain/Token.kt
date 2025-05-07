package hung.deptrai.mycomic.core.domain

import kotlinx.serialization.Serializable

@Serializable
data class Token(
    val accessToken: String = "",
    val refreshToken: String = "",
    val expiresAt: Long = 0L
)