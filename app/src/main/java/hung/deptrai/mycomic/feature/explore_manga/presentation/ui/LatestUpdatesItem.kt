package hung.deptrai.mycomic.feature.explore_manga.presentation.ui


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import hung.deptrai.mycomic.core.utils.MdLang
import hung.deptrai.mycomic.feature.explore_manga.domain.MangaHome
import java.time.Duration
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MangaCard(
    manga: MangaHome,
    action: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Row con 1: ảnh + column chứa tên truyện, chương, nhóm dịch
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(manga.coverArt),
                    contentDescription = manga.title,
                    modifier = Modifier.size(90.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier.height(80.dp), // cùng chiều cao với ảnh
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = manga.title ?: "", style = MaterialTheme.typography.titleMedium)
                    Row {
                        Icon(
                            painter = painterResource(
                                MdLang.fromIsoCode(
                                    manga.lastUpdatedChapter?.translatedLang ?: ""
                                )?.iconResId ?: 1
                            ),
                            contentDescription = MdLang.fromIsoCode(
                                manga.lastUpdatedChapter?.translatedLang ?: ""
                            )?.lang
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text =
                            if (!manga.lastUpdatedChapter?.vol.isNullOrEmpty()) {
                                "Vol. " + manga.lastUpdatedChapter?.vol
                            } else {
                                ""
                            } +
                                    if (!manga.lastUpdatedChapter?.chapter.isNullOrEmpty()) {
                                        "Ch. " + manga.lastUpdatedChapter?.chapter
                                    } else {
                                        ""
                                    } +
                                    if (!manga.lastUpdatedChapter?.title.isNullOrEmpty()) {
                                        " - " + manga.lastUpdatedChapter?.title
                                    } else {
                                        ""
                                    },
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Text(
                        text =
                        if (!manga.lastUpdatedChapter?.scanlationGroup.isNullOrEmpty()) {
                            manga.lastUpdatedChapter?.scanlationGroup ?: ""
                        } else {
                            "No Group"
                        }, style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            // Row con 2: icon + readableAt
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.height(80.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Comment,
                    contentDescription = "Comment"
                )
                Spacer(Modifier.width(2.dp))
                Text(
                    text = manga.lastUpdatedChapter?.commentCount.toString(),
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    text = getTimeAgoFromIsoDate(manga.lastUpdatedChapter?.updatedAt ?: ""),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getTimeAgoFromIsoDate(isoDate: String): String {
    return try {
        val updatedTime = OffsetDateTime.parse(isoDate, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val now = OffsetDateTime.now()
        val duration = Duration.between(updatedTime, now)

        when {
            duration.toMinutes() < 1 -> "Now"
            duration.toHours() < 1 -> "${duration.toMinutes()}m"
            duration.toHours() < 24 -> "${duration.toHours()}h"
            duration.toDays() < 7 -> "${duration.toDays()}d"
            duration.toDays() < 30 -> "${duration.toDays() / 7}w"
            duration.toDays() < 365 -> "${duration.toDays() / 30}mo"
            else -> "${duration.toDays() / 365}y"
        }
    } catch (e: Exception) {
        "?"
    }
}