package hung.deptrai.mycomic.core.domain.mapper

import hung.deptrai.mycomic.core.domain.model.AuthorEntity
import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorDTO

fun AuthorDTOtoAuthorEntity (
    authorDTO: hung.deptrai.mycomic.feature.search.data.dto.author.AuthorDTO
): AuthorEntity{
    val attr = authorDTO.attributes
    return AuthorEntity(
        twitter = attr.twitter,
        website = attr.website,
        version = attr.version ?: 1,
        id = authorDTO.id,
        name = attr.name ?: "",
        createdAt = attr.createdAt ?: "",
        updatedAt = attr.updatedAt ?: "",
        relationships = authorDTO.relationships,
        biography = attr.biography,
        booth = attr.booth,
        naver = attr.naver,
        pixiv = attr.pixiv,
        weibo = attr.weibo,
        fanBox = attr.fanBox,
        imageUrl = attr.imageUrl,
        tumblr = attr.tumblr,
        youtube = attr.youtube
    )
}