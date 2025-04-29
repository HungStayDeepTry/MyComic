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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import hung.deptrai.mycomic.feature.search.presentation.SearchComic
import hung.deptrai.mycomic.R
import hung.deptrai.mycomic.feature.search.presentation.Result
import hung.deptrai.mycomic.feature.search.presentation.TagSearch
import hung.deptrai.mycomic.feature.search.presentation.ui.util.getStatusColor
@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("DefaultLocale")
@Composable
fun MangaSearchResultItem(
    manga: SearchComic,
    tagState: Result<List<TagSearch>>,
    onItemClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onItemClick(manga.id) }
    ) {
        // Card chứa nội dung
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier = Modifier
                    .height(100.dp),
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
                        .padding(top = 2.dp)
                ) {
                    Text(
                        text = manga.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onBackground
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
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Row(Modifier.width(73.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(R.drawable.un_bookmark_ic),
                                contentDescription = stringResource(R.string.bookmark_count)
                            )
                            Text(
                                text = formatNumberShort(manga.follows ?: 0),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground
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
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground
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
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
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
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        if (tagState is Result.Success) {
                            val matchedTags = tagState.data.filter { it.id in manga.tags }
                            FlowRow( // cần androidx.compose.foundation.layout.FlowRow
                                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                matchedTags.forEach { tag ->
                                    Text(
                                        text = tag.name,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(MaterialTheme.colorScheme.surfaceVariant)
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = manga.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground
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
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background
                        ) // Thay màu White bằng màu nền của card nếu cần
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

@Preview
@Composable
private fun Prev5() {
//    MangaSearchResultItem(
//        manga = SearchComic(id = "1", imageUrl = "", description = "123", title = "title1", views = 123123, rating = 9.1, averageRating = 9.1, status = "ongoing", authors = listOf("hung"), bayesianRating = 9.2, coverArtUrl = "", follows = 123123, chapters = 23, commentsCount = 2222), {}
//    )
}