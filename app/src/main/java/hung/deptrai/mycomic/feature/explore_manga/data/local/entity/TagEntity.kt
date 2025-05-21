package hung.deptrai.mycomic.feature.explore_manga.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "tag")
data class TagEntity(
    @PrimaryKey val id: String,
    val name: String,
    val group: String
)
@Entity(tableName = "manga_tag_cross_ref", primaryKeys = ["mangaId", "tagId"])
data class MangaTagCrossRef(
    val mangaId: String,
    val tagId: String
)
data class MangaWithTags(
    @Embedded val manga: HomeMangaEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(MangaTagCrossRef::class)
    )
    val tags: List<TagEntity>
)
data class TagWithMangas(
    @Embedded val tag: TagEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(MangaTagCrossRef::class)
    )
    val mangas: List<HomeMangaEntity>
)