package hung.deptrai.mycomic.feature.explore_manga.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import hung.deptrai.mycomic.feature.explore_manga.domain.MangaHome

@Composable
fun PopularNewTitlesItem(
    manga: MangaHome,
    action: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Column(
        modifier = Modifier
            .width(screenWidth)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Cột trái chứa hình ảnh
            Card(
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(manga.coverArt),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Cột phải chứa tiêu đề + tác giả + nghệ sĩ
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = manga.title ?: "No Title",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 6,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = listOf(manga.authorName, manga.artist)
                        .filter { it?.isNotBlank() == true }
                        .joinToString(" - "),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Dòng thứ 2 chứa các tags
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(manga.tags.size) { tag ->
                Card(
                    shape = MaterialTheme.shapes.small,
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Text(
                        text = manga.tags[tag].name ?: "",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}