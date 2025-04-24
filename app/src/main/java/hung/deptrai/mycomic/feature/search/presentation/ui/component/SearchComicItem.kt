package hung.deptrai.mycomic.feature.search.presentation.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import hung.deptrai.mycomic.feature.search.presentation.SearchComic
import hung.deptrai.mycomic.R

/**
 * Data model representing a summary item for MangaDex search results.
 */
data class MangaSummary(
    val id: String,
    val title: String,
    val authors: String,
    val thumbnailUrl: String
)

/**
 * Composable for displaying a single manga item in search results, mimicking MangaDex mobile web style.
 *
 * @param manga The MangaSummary data containing title, authors, and thumbnail URL.
 * @param modifier Optional Modifier for styling.
 * @param onItemClick Callback when the item is clicked, returning the manga's ID.
 */
@Composable
fun MangaSearchResultItem(
    manga: SearchComic,
    onItemClick: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                onItemClick(manga.id)
            }
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
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = manga.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row{
                    Row {
                        Icon(
                            painter = painterResource(R.drawable.un_star_ic),
                            contentDescription = stringResource(R.string.rating)
                        )
                        Text(
                            text = manga.bayesianRating?.toFloat().toString(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Row {
                        Icon(
                            painter = painterResource(R.drawable.un_bookmark_ic),
                            contentDescription = stringResource(R.string.bookmark_count)
                        )
                        Text(
                            text = formatNumberShort(manga.follows?:0),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Row {
                        Icon(
                            painter = painterResource(R.drawable.view_ic),
                            contentDescription = stringResource(R.string.NA)
                        )
                        Text(
                            text = "N/A",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Row {
                        Icon(
                            painter = painterResource(R.drawable.comment_regular),
                            contentDescription = stringResource(R.string.comment_count)
                        )
                        Text(
                            text = formatNumberShort(manga.commentsCount?:0).toString(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Row{
                    Icon(
                        painter = painterResource(R.drawable.super_dot),
                        contentDescription = stringResource(R.string.status_comic)
                    )
                    Text(
                        text = manga.status,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
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