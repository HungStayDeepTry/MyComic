package hung.deptrai.mycomic.core.domain.mapper

import android.util.Log
import hung.deptrai.mycomic.core.domain.model.MangaEntity

fun mangaDTOtoMangaEntity(
    mangaDTO: hung.deptrai.mycomic.feature.search.data.dto.MangaDTO,
    coverArtDTO: hung.deptrai.mycomic.feature.search.data.dto.coverArt.CoverArtDTO,
    authorDTO: List<hung.deptrai.mycomic.feature.search.data.dto.author.AuthorDTO>,
    statisticDTO: hung.deptrai.mycomic.feature.search.data.dto.statistic.MangaStatisticDTO
): MangaEntity {
        val attr = mangaDTO.attributes

        val altTitles = attr.altTitles
        val description = attr.description
        val title = attr.title
        val links = attr.links

        val artists = mangaDTO.relationships.filter { it.type == "artist" }

        val genres = attr.tags.filter { it.attributes.group == "genre" }
        val themes = attr.tags.filter { it.attributes.group == "theme" }
        val contents = attr.tags.filter { it.attributes.group == "content" }
        val formats = attr.tags.filter { it.attributes.group == "format" }

        val rs = MangaEntity(
            id = mangaDTO.id,
            title = title,
            altTitles = altTitles,
            description = description,
            coverArt = coverArtDTO,
            authors = authorDTO,
            artists = artists,
            genres = genres,
            themes = themes,
            contents = contents,
            formats = formats,
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
            updatedAt = attr.updatedAt,
            // 👇 Map thống kê
            follows = statisticDTO.follows,
            averageRating = statisticDTO.rating.average,
            bayesianRating = statisticDTO.rating.bayesian,
            commentsCount = statisticDTO.comments?.repliesCount
        )
    Log.e("MapDTO", "mapDtoToEntity: ${rs.title}", )
    return rs
}