import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import hung.deptrai.mycomic.feature.explore_manga.domain.MangaHome
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun PopularNewTitlesItem(
    manga: MangaHome,
    action: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Box(
        modifier = Modifier
            .width(screenWidth)
            .height(350.dp) // Tùy bạn điều chỉnh chiều cao theo UI bạn mong muốn
    ) {
        // Ảnh nền làm mờ
        Image(
            painter = rememberAsyncImagePainter(manga.coverArt),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .blur(5.dp), // Làm mờ ảnh nền
            contentScale = ContentScale.Crop
        )

        // Lớp nền mờ tối nhẹ để nội dung nổi bật hơn
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )
        // Nội dung foreground
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Spacer(Modifier.height(50.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Hình ảnh
                Card(
                    modifier = Modifier
                        .width(120.dp)
                        .fillMaxHeight(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(manga.coverArt),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                // Tiêu đề và tác giả
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
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White
                    )

                    Text(
                        text = listOf(manga.authorName, manga.artist)
                            .filter { it?.isNotBlank() == true }
                            .joinToString(" - "),
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Tags
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
}
