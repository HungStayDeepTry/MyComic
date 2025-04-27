package hung.deptrai.mycomic.feature.search.presentation.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import hung.deptrai.mycomic.feature.search.presentation.SearchComic
import hung.deptrai.mycomic.R
import hung.deptrai.mycomic.feature.search.presentation.ui.util.getStatusColor
@SuppressLint("DefaultLocale")
@Composable
fun MangaSearchResultItem(
    manga: SearchComic,
    onItemClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onItemClick(manga.id) }
    ) {
        // Card chứa nội dung
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.height(100.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                AsyncImage(
                    model = manga.coverArtUrl,
                    contentDescription = "Thumbnail of ${manga.title}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(80.dp)
                        .fillMaxHeight()
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    Text(
                        text = manga.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(Modifier.width(60.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(R.drawable.un_star_ic),
                                contentDescription = stringResource(R.string.rating)
                            )
                            Spacer(Modifier.width(2.dp))
                            Text(
                                text = String.format("%.2f", manga.bayesianRating?.toFloat() ?: 0f),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Row(Modifier.width(73.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(R.drawable.un_bookmark_ic),
                                contentDescription = stringResource(R.string.bookmark_count)
                            )
                            Text(
                                text = formatNumberShort(manga.follows ?: 0),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Row(Modifier.width(55.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(R.drawable.view_ic),
                                contentDescription = stringResource(R.string.NA)
                            )
                            Spacer(Modifier.width(2.dp))
                            Text(
                                text = "N/A",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Column {
                            Spacer(Modifier.height(2.dp))
                            Row(
                                Modifier.width(70.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.padding(horizontal = 4.dp),
                                    painter = painterResource(R.drawable.comment_regular),
                                    contentDescription = stringResource(R.string.comment_count)
                                )
                                Text(
                                    text = formatNumberShort(manga.commentsCount ?: 0).toString(),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clip(RoundedCornerShape(5.dp))
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        Icon(
                            modifier = Modifier.padding(start = 4.dp),
                            painter = painterResource(R.drawable.super_dot),
                            contentDescription = stringResource(R.string.status_comic),
                            tint = getStatusColor(manga.status)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            modifier = Modifier.padding(end = 4.dp),
                            text = manga.status,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = manga.description,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        // Overlay để làm mờ dần phần dưới của Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .align(Alignment.BottomStart) // Sử dụng Align ở đây để đặt gradient ở phía dưới cùng của Card
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.White) // Thay màu White bằng màu nền của card nếu cần
                    )
                )
        )
    }
}

@SuppressLint("DefaultLocale")
fun formatNumberShort(value: Int): String {
    return when {
        value >= 1_000_000_000 -> String.format("%.1fB", value / 1_000_000_000f)
        value >= 1_000_000 -> String.format("%.1fM", value / 1_000_000f)
        value >= 1_000 -> String.format("%.1fK", value / 1_000f)
        else -> value.toString()
    }
}