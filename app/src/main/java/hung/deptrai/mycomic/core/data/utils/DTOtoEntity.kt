package hung.deptrai.mycomic.core.data.utils

import hung.deptrai.mycomic.core.data.remote.dto.ChapterDTO
import hung.deptrai.mycomic.core.data.remote.dto.scanlationGroup.ScanlationGroupAttributes
import hung.deptrai.mycomic.core.data.remote.dto.statistic.MangaStatisticDTO
import hung.deptrai.mycomic.core.data.remote.dto.statistic.StatisticsResponse
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject
import hung.deptrai.mycomic.core.data.remote.dto.wrapper.DTOject1
import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.ChapterEntity

fun chapterDTOtoChapterEntity(
    chapterDTO: DTOject<ChapterDTO>,
    statisticDTO: MangaStatisticDTO,
    scanGroupDTO: DTOject<ScanlationGroupAttributes>
): ChapterEntity{
    return ChapterEntity(
        chapter = chapterDTO.attributes.chapter ?: "",
        id = chapterDTO.id,
        commentCount = statisticDTO.comments?.repliesCount,
        translatedLang = chapterDTO.attributes.translatedLanguage ?: "",
        updatedAt = chapterDTO.attributes.updatedAt ?: "",
        title = chapterDTO.attributes.title,
        volume = chapterDTO.attributes.volume ?: "",
        mangaId = chapterDTO.relationships.firstOrNull {
            it.type == "manga"
        }?.id.toString(),
        scanGroup = scanGroupDTO.attributes.name
    )
}