package hung.deptrai.mycomic.core.domain.mapper

import android.util.Log
import hung.deptrai.mycomic.core.data.dto.DTOject
//import hung.deptrai.mycomic.core.domain.model.MangaEntity
import hung.deptrai.mycomic.feature.search.data.dto.Attributes
import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorAttributes
import hung.deptrai.mycomic.feature.search.data.dto.coverArt.CoverArtAttributes
import hung.deptrai.mycomic.feature.search.data.dto.statistic.MangaStatisticDTO
import hung.deptrai.mycomic.feature.search.data.dto.tag.TagAttributesDTO
import hung.deptrai.mycomic.feature.search.domain.model.SearchComic
import hung.deptrai.mycomic.feature.search.domain.model.TagSearch

fun mangaDTOtoMangaSearch(
    mangaDTO: DTOject<Attributes>,
    coverArtDTO: DTOject<CoverArtAttributes>,
    authorDTO: List<DTOject<AuthorAttributes>>,
    statisticDTO: MangaStatisticDTO,
    tagSearch: List<TagSearch>
): SearchComic {
        val attr = mangaDTO.attributes

//        val altTitles = attr.altTitles
        val description = attr.description
        val title = attr.title
//        val links = attr.links
//
//        val artists = mangaDTO.relationships.filter { it.type == "artist" }
//
//        val genres = attr.tags.filter { it.attributes.group == "genre" }
//        val themes = attr.tags.filter { it.attributes.group == "theme" }
//        val contents = attr.tags.filter { it.attributes.group == "content" }
//        val formats = attr.tags.filter { it.attributes.group == "format" }
        val authors = authorDTO.map {
            it.attributes.name ?: ""
        }


        val rs = SearchComic(
            id = mangaDTO.id,
            title = title.en,
            description = description.en,
            authors = authors,
            contentRating = attr.contentRating,
            status = attr.status,
            // 👇 Map thống kê
            follows = statisticDTO.follows,
            averageRating = statisticDTO.rating.average,
            bayesianRating = statisticDTO.rating.bayesian,
            commentsCount = statisticDTO.comments?.repliesCount,
            tags = tagSearch,
            views = 0,
            coverArtUrl = coverArtDTO.attributes.fileName,
            rating = statisticDTO.rating.average,
            chapters = mangaDTO.attributes.lastChapter?.toInt() ?: 0,
        )
    Log.e("MapDTO", "mapDtoToEntity: ${rs.title}", )
    return rs
}