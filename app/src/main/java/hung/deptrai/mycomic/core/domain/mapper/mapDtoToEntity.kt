package hung.deptrai.mycomic.core.domain.mapper

import android.util.Log
import hung.deptrai.mycomic.core.domain.model.MangaEntity
import hung.deptrai.mycomic.feature.search.data.remote.dto.Data
import hung.deptrai.mycomic.feature.search.data.remote.dto.MangaDTO
import hung.deptrai.mycomic.feature.search.data.remote.dto.author.AuthorDTO
import hung.deptrai.mycomic.feature.search.data.remote.dto.coverArt.CoverArtDTO
import hung.deptrai.mycomic.feature.search.data.remote.dto.statistic.MangaStatisticDTO
import kotlin.math.log

fun mapDtoToEntity(
    data: Data,
    coverArtDTO: CoverArtDTO,
    authorDTO: List<AuthorDTO>,
    statisticDTO: MangaStatisticDTO
): MangaEntity {
        val attr = data.attributes

        val altTitles = attr.altTitles
        val description = attr.description
        val title = attr.title
        val links = attr.links

        val artists = data.relationships.filter { it.type == "artist" }

        val genres = attr.tags.filter { it.attributes.group == "genre" }
        val themes = attr.tags.filter { it.attributes.group == "theme" }

        val rs = MangaEntity(
            id = data.id,
            title = title,
            altTitles = altTitles,
            description = description,
            coverArt = coverArtDTO,
            authors = authorDTO,
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