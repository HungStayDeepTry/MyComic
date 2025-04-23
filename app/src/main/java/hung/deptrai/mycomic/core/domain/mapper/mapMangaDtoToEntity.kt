package hung.deptrai.mycomic.core.domain.mapper

import hung.deptrai.mycomic.core.domain.model.MangaEntity
import hung.deptrai.mycomic.feature.search.data.remote.dto.MangaDTO

fun mapMangaDtoToEntity(dto: MangaDTO): List<MangaEntity> {
    return dto.data.map { data ->
        val attr = data.attributes

        val altTitles = attr.altTitles
        val description = attr.description
        val title = attr.title
        val links = attr.links

        val coverArt = data.relationships.firstOrNull { it.type == "cover_art" }
        val authors = data.relationships.filter { it.type == "author" }
        val artists = data.relationships.filter { it.type == "artist" }

        val genres = attr.tags.filter { it.attributes.group == "genre" }
        val themes = attr.tags.filter { it.attributes.group == "theme" }

        MangaEntity(
            id = data.id,
            title = title,
            altTitles = altTitles,
            description = description,
            coverArt = coverArt,
            authors = authors,
            artists = artists,
            genres = genres,
            themes = themes,
            contentRating = attr.contentRating,
            publicationDemographic = attr.publicationDemographic,
            originalLanguage = attr.originalLanguage,
            status = attr.status,
            state = attr.state,
            lastChapter = attr.lastChapter,
            lastVolume = attr.lastVolume,
            latestUploadedChapter = attr.latestUploadedChapter,
            year = attr.year,
            externalLinks = links,
            createdAt = attr.createdAt,
            updatedAt = attr.updatedAt
        )
    }
}