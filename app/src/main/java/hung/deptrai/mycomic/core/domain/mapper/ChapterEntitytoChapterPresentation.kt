package hung.deptrai.mycomic.core.domain.mapper

import hung.deptrai.mycomic.feature.explore_manga.data.local.entity.ChapterEntity
import hung.deptrai.mycomic.feature.explore_manga.domain.ChapterHome

fun chapterEntitytoChapterHome(
    chapterEntity: ChapterEntity
): ChapterHome{
    return ChapterHome(
        commentCount = chapterEntity.commentCount,
        chapter = chapterEntity.chapter,
        id = chapterEntity.id,
        vol = chapterEntity.volume,
        translatedLang = chapterEntity.translatedLang,
        updatedAt = chapterEntity.updatedAt,
        title = chapterEntity.title,
        scanlationGroup =
    )
}