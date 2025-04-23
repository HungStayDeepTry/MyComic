package hung.deptrai.mycomic.core.domain.model

import hung.deptrai.mycomic.feature.search.data.remote.dto.AltTitle
import hung.deptrai.mycomic.feature.search.data.remote.dto.Description
import hung.deptrai.mycomic.feature.search.data.remote.dto.Links
import hung.deptrai.mycomic.feature.search.data.remote.dto.Relationship
import hung.deptrai.mycomic.feature.search.data.remote.dto.Tag
import hung.deptrai.mycomic.feature.search.data.remote.dto.Title
import hung.deptrai.mycomic.feature.search.data.remote.dto.author.AuthorDTO
import hung.deptrai.mycomic.feature.search.data.remote.dto.coverArt.CoverArtDTO

data class MangaEntity(
    val id: String,
    val title: Title,                              // Dùng luôn Title từ DTO
    val altTitles: List<AltTitle>,                // Dùng luôn AltTitle
    val description: Description?,                // Dùng luôn Description
    val coverArt: CoverArtDTO?,                     // Thay đổi kiểu để rõ ràng hơn về cover art
    val authors: List<AuthorDTO>,                    // Thay đổi kiểu để rõ ràng hơn về authors
    val artists: List<Relationship>,              // relationship.type == "artist"
    val genres: List<Tag>,                        // tag.attributes.group == "genre"
    val themes: List<Tag>,                        // tag.attributes.group == "theme"
    val contentRating: String?,
    val publicationDemographic: String?,
    val originalLanguage: String?,
    val status: String?,
    val state: String?,
    val lastChapter: String?,
    val lastVolume: String?,
    val latestUploadedChapter: String?,
    val year: Int?,
    val externalLinks: Links,                     // Dùng luôn Links DTO
    val createdAt: String,
    val updatedAt: String
)