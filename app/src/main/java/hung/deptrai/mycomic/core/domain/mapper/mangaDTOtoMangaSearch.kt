package hung.deptrai.mycomic.core.domain.mapper

import android.util.Log
import hung.deptrai.mycomic.core.data.dto.wrapper.DTOject
//import hung.deptrai.mycomic.core.domain.model.MangaEntity
import hung.deptrai.mycomic.core.data.dto.Attributes
import hung.deptrai.mycomic.core.data.dto.ChapterDTO
import hung.deptrai.mycomic.core.data.dto.artist.ArtistAttributes
import hung.deptrai.mycomic.core.data.dto.author.AuthorAttributes
import hung.deptrai.mycomic.core.data.dto.coverArt.CoverArtAttributes
import hung.deptrai.mycomic.core.data.dto.scanlationGroup.ScanlationGroupAttributes
import hung.deptrai.mycomic.core.data.dto.scanlationGroup.ScanlationGroupDTO
import hung.deptrai.mycomic.core.data.dto.statistic.MangaStatisticDTO
import hung.deptrai.mycomic.feature.search.domain.model.SearchComic
import hung.deptrai.mycomic.core.domain.model.Tag
import hung.deptrai.mycomic.feature.explore_manga.domain.ChapterHome
import hung.deptrai.mycomic.feature.explore_manga.domain.MangaHome

fun mangaDTOtoMangaSearch(
    mangaDTO: DTOject<Attributes>,
    coverArtDTO: DTOject<CoverArtAttributes>,
    authorDTO: List<DTOject<AuthorAttributes>>,
    statisticDTO: MangaStatisticDTO
): SearchComic {
        val tags = mangaDTO.attributes.tags.map {
            Tag(
                id = it.id,
                name = it.attributes.name.en,
                group = it.attributes.group
            )
        }
        val attr = mangaDTO.attributes
        val authors = authorDTO.map {
            it.attributes.name ?: ""
        }
        val title = attr.title.en ?: "No Title"
        val description = attr.description.en ?: "No Description"
        val contentRating = attr.contentRating ?: "Unknown"
        val coverArtUrl = if (coverArtDTO.attributes.fileName != null) {
            "https://uploads.mangadex.org/covers/${mangaDTO.id}/${coverArtDTO.attributes.fileName}"
        } else {
            ""
        }

        val rs = SearchComic(
            id = mangaDTO.id,
            title = title,
            description = description,
            authors = authors,
            contentRating = contentRating,
            status = attr.status ?: "",
            // 👇 Map thống kê
            follows = statisticDTO.follows,
            averageRating = statisticDTO.rating.average,
            bayesianRating = statisticDTO.rating.bayesian,
            commentsCount = statisticDTO.comments?.repliesCount,
            tags = tags,
            views = 0,
            coverArtUrl = coverArtUrl,
            rating = statisticDTO.rating.average,
            chapters = mangaDTO.attributes.lastChapter?.toIntOrNull() ?: 0,
        )
    Log.e("MapDTO", "mapDtoToEntity: ${rs.title}", )
    return rs
}

fun mangaDTOtoMangaHome(
    mangaDTO: DTOject<Attributes>,
    coverArtDTO: DTOject<CoverArtAttributes>,
    authorDTO: List<DTOject<AuthorAttributes>>,
    artistDTO: DTOject<ArtistAttributes>,
    statisticDTO: MangaStatisticDTO,
    chapter: DTOject<ChapterDTO>,
    scanlationGroup: DTOject<ScanlationGroupAttributes>
): MangaHome{
    val tags = mangaDTO.attributes.tags.map {
        Tag(
            id = it.id,
            name = it.attributes.name.en,
            group = it.attributes.group
        )
    }
    val attr = mangaDTO.attributes
    val authors = authorDTO.map {
        it.attributes.name ?: ""
    }
    val title = attr.title.en ?: "No Title"
    val description = attr.description.en ?: "No Description"
    val contentRating = attr.contentRating ?: "Unknown"
    val coverArtUrl = if (coverArtDTO.attributes.fileName != null) {
        "https://uploads.mangadex.org/covers/${mangaDTO.id}/${coverArtDTO.attributes.fileName}"
    } else {
        ""
    }

    val chapterHome = ChapterHome(
        id = chapter.id,
        title = chapter.attributes.title,
        updatedAt = chapter.attributes.updatedAt ?: "",
        chapter = chapter.attributes.chapter ?: "",
        vol = chapter.attributes.volume ?: "",
        translatedLang = chapter.attributes.translatedLanguage ?: "",
        scanlationGroup = scanlationGroup.attributes.name
    )
    return MangaHome(
        id = mangaDTO.id,
        title = mangaDTO.attributes.title.en,
        tags = tags,
        lastUpdatedChapter = chapterHome,
        commentCount = statisticDTO.comments?.repliesCount,
        originalLang = mangaDTO.attributes.originalLanguage,
        authorName = authors[0],
        artist = artistDTO.attributes.name,
        coverArt = coverArtUrl
    )
}
//
//fun chapterDTOtoChapterHome(
//    chapter: DTOject<ChapterDTO>
//)