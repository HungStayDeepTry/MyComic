package hung.deptrai.mycomic.feature.search.presentation.basicSearch.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
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
import hung.deptrai.mycomic.R
import hung.deptrai.mycomic.feature.search.domain.model.SearchComic
import hung.deptrai.mycomic.feature.search.domain.model.TagSearch
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.ui.util.getStatusColor

@SuppressLint("DefaultLocale")
@Composable
fun MangaSearchResultItem(
    manga: SearchComic,
    onItemClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onItemClick(manga.id) }
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
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
                        .padding(top = 2.dp, end = 8.dp)
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

//                    Spacer(modifier = Modifier.height(2.dp))

                    // ⭐ Ratings + Follow + Views + Comments
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
                                text = formatNumberShort(manga.commentsCount ?: 0),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }

                    // ⭐ STATUS hiển thị riêng, phía trên tag
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // STATUS
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(5.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.super_dot),
                                contentDescription = stringResource(R.string.status_comic),
                                tint = getStatusColor(manga.status),
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = manga.status,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        val matchedTags = manga.tags // không cần kiểm tra tagState nữa

                        // Sắp xếp tag theo ưu tiên và tên
                        val orderedTags = matchedTags.sortedWith(
                            compareBy({ getTagPriority(it) }, { it.name })
                        )

                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Content Rating tag trước tiên
                            manga.contentRating?.let { rating ->
                                val bgColor = getContentRatingBackgroundColor(rating)
                                Text(
                                    text = rating.replaceFirstChar { it.uppercase() },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = getContentRatingTextColor(rating),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(bgColor ?: MaterialTheme.colorScheme.surfaceVariant)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }

                            // Sau đó là các tag
                            orderedTags.forEach { tag ->
                                val bgColor = getTagBackgroundColor(tag)
                                Text(
                                    text = tag.name,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = getTagTextColor(bgColor),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(bgColor ?: MaterialTheme.colorScheme.surfaceVariant)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(4.dp))

                    // ⭐ Mô tả
                    Text(
                        text = manga.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        // Gradient mờ cuối card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .align(Alignment.BottomStart)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background
                        )
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
// Ưu tiên sắp xếp
fun getTagPriority(tag: TagSearch): Int {
    return when (tag.group.lowercase()) {
        "content" -> 1
        "format" -> 2
        "genre" -> 3
        "theme" -> 4
        else -> 5
    }
}

// Màu background cho tag
fun getTagBackgroundColor(tag: TagSearch): Color? {
    return when (tag.group.lowercase()) {
        "content" -> Color(0xFFE57373) // đỏ
        "format" -> if (tag.name.equals("doujinshi", ignoreCase = true)) Color(0xFFBA68C8) else null
        else -> null
    }
}

// Màu chữ cho tag
@Composable
fun getTagTextColor(background: Color?): Color {
    return if (background != null) Color.White else MaterialTheme.colorScheme.onBackground
}

// Màu cho contentRating
@Composable
fun getContentRatingBackgroundColor(rating: String?): Color? {
    return when (rating?.lowercase()) {
        "erotica", "pornographic" -> Color(0xFFE57373)
        "safe" -> Color(0xFF64B5F6)
        "suggestive" -> Color(0xFFFFC107)
        else -> null
    }
}

@Composable
fun getContentRatingTextColor(rating: String?): Color {
    return if (getContentRatingBackgroundColor(rating) != null) Color.White
    else MaterialTheme.colorScheme.onBackground
}

@Preview
@Composable
private fun Prev5() {
//    MangaSearchResultItem(
//        manga = SearchComic(id = "1", imageUrl = "", description = "123", title = "title1", views = 123123, rating = 9.1, averageRating = 9.1, status = "ongoing", authors = listOf("hung"), bayesianRating = 9.2, coverArtUrl = "", follows = 123123, chapters = 23, commentsCount = 2222), {}
//    )
}