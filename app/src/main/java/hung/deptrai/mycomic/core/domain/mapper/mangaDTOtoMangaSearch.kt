package hung.deptrai.mycomic.core.domain.mapper

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject
//import hung.deptrai.mycomic.core.domain.model.MangaEntity
import hung.deptrai.mycomic.core.data.remote.dto.Attributes
import hung.deptrai.mycomic.core.data.remote.dto.IncludesAttributesDto
import hung.deptrai.mycomic.core.data.remote.dto.author.AuthorAttributes
import hung.deptrai.mycomic.core.data.remote.dto.statistic.MangaStatisticDTO
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject1
import hung.deptrai.mycomic.feature.search.domain.model.SearchComic
import hung.deptrai.mycomic.core.domain.model.Tag
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.HomeMangaEntity
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.TagEntity
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun mangaDTOtoMangaSearch(
    mangaDTO: hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject<hung.deptrai.mycomic.core.data.remote.dto.Attributes>,
    coverArtDTO: hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject<hung.deptrai.mycomic.core.data.remote.dto.coverArt.CoverArtAttributes>,
    authorDTO: List<hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject<hung.deptrai.mycomic.core.data.remote.dto.author.AuthorAttributes>>,
    statisticDTO: hung.deptrai.mycomic.core.data.remote.dto.statistic.MangaStatisticDTO
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
//fun ChapterDTO.toChapterHome(
//    chapter: DTOject<ChapterDTO>
//): ChapterHome{
//    return ChapterHome(
//        id = chapter?.id ?: "",
//        title = chapter?.attributes?.title,
//        updatedAt = chapter?.attributes?.updatedAt ?: "",
//        chapter = chapter?.attributes?.chapter ?: "",
//        vol = chapter?.attributes?.volume ?: "",
//        translatedLang = chapter?.attributes?.translatedLanguage ?: "",
//        scanlationGroup = scanlationGroup?.attributes?.name
//    )
//}


@RequiresApi(Build.VERSION_CODES.O)
fun mangaDTOtoMangaEntity(
    mangaDTO: DTOject1<Attributes>,
    coverArtDTO: IncludesAttributesDto?,
    authorDTO: List<DTOject<AuthorAttributes>>? = null,
    artistDTO: List<DTOject<AuthorAttributes>>? = null,
    statisticDTO: MangaStatisticDTO? = null,
    customType: Int
): Pair<HomeMangaEntity, List<TagEntity>> {
    val authors = authorDTO?.map {
        it.attributes.name ?: ""
    }
    Log.d("MangaMapper", "Authors: $authors")

    val artist = artistDTO?.map {
        it.attributes.name ?: ""
    }
    Log.d("MangaMapper", "Artists: $artist")

    val coverArtUrl = if (coverArtDTO?.fileName != null) {
        "https://uploads.mangadex.org/covers/${mangaDTO.id}/${coverArtDTO.fileName}"
    } else {
        ""
    }
    Log.d("MangaMapper", "Cover Art URL: $coverArtUrl")

    val tags = mangaDTO.attributes.tags.map { tagDTO ->
        TagEntity(
            id = tagDTO.id,
            name = tagDTO.attributes.name.en,
            group = tagDTO.attributes.group
        )
    }
    Log.d("MangaMapper", "Tags: $tags")

    val title = mangaDTO.attributes.title.en
    Log.d("MangaMapper", "Title: $title")

    val commentCount = statisticDTO?.comments?.repliesCount ?: 0
    Log.d("MangaMapper", "Comment count: $commentCount")

    val originalLang = mangaDTO.attributes.originalLanguage
    Log.d("MangaMapper", "Original language: $originalLang")

    val contentRating = mangaDTO.attributes.contentRating
    Log.d("MangaMapper", "Content rating: $contentRating")

    val createdAt = parseDateToMillis(mangaDTO.attributes.createdAt)
    Log.d("MangaMapper", "Created at (millis): $createdAt")

    val latestUploadedChapter = mangaDTO.attributes.latestUploadedChapter
    Log.d("MangaMapper", "Latest uploaded chapter: $latestUploadedChapter")

    Log.d("MangaMapper", "Type: $customType")


    val homeMG = HomeMangaEntity(
        id = mangaDTO.id,
        title = title,
        commentCount = commentCount,
        originalLang = originalLang,
        authorName = authors?.joinToString(", ") ?: "",
        artist = artist?.joinToString(", ") ?: "",
        contentRating = contentRating,
        createdAt = createdAt,
        latestUploadedChapter = latestUploadedChapter ?: "",
        coverArtLink = coverArtUrl,
        customType = customType
    )
    Log.d("MangaMapper", "HomeMangaEntity created: $homeMG")

    Log.d("MangaMapper", "-----------------------------------")

    return Pair(homeMG, tags)
}

@RequiresApi(Build.VERSION_CODES.O)
fun parseDateToMillis(dateStr: String): Long {
    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val offsetDateTime = OffsetDateTime.parse(dateStr, formatter)
    return offsetDateTime.toInstant().toEpochMilli()
}
//
//fun chapterDTOtoChapterHome(
//    chapter: DTOject<ChapterDTO>
//)