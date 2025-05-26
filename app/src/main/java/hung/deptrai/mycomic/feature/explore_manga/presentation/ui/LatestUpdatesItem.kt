package hung.deptrai.mycomic.feature.explore_manga.presentation.ui


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import hung.deptrai.mycomic.R
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Card chỉ bao quanh ảnh
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .height(90.dp) // chiều cao ảnh
                    .width(70.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(manga.coverArt),
                    contentDescription = manga.title,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Column chứa thông tin manga
        Column(
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth()
                .padding(
                    top = 2.dp,
                    bottom = 4.dp
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Row 1: Tiêu đề
            Text(
                text = manga.title ?: "",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Row 2: Vol + chap + title và comment
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(
                            MdLang.fromIsoCode(
                                manga.lastUpdatedChapter?.translatedLang ?: ""
                            )?.iconResId ?: 1
                        ),
                        contentDescription = MdLang.fromIsoCode(
                            manga.lastUpdatedChapter?.translatedLang ?: ""
                        )?.lang,
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = listOfNotNull(
                            manga.lastUpdatedChapter?.vol?.let { "Vol. $it" },
                            manga.lastUpdatedChapter?.chapter?.let { "Ch. $it" },
                            manga.lastUpdatedChapter?.title?.let { "- $it" }
                        ).joinToString(" "),
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.width(8.dp)) // cách 1 chút cho an toàn

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_comment_24dp),
                        contentDescription = "Comment",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = manga.lastUpdatedChapter?.commentCount?.toString() ?: "0",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            // Row 3: Nhóm dịch và thời gian cập nhật
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chapter_group_24dp),
                        contentDescription = "Group",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = manga.lastUpdatedChapter?.scanlationGroup ?: "No Group",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Text(
                    text = getTimeAgoFromIsoDate(manga.lastUpdatedChapter?.updatedAt ?: ""),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onBackground
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