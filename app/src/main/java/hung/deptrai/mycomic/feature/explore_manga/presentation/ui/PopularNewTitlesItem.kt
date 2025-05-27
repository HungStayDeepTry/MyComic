import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.zIndex
import hung.deptrai.mycomic.core.presentation.components.getContentRatingBackgroundColor
import hung.deptrai.mycomic.core.presentation.components.getContentRatingTextColor
import hung.deptrai.mycomic.core.presentation.components.getTagBackgroundColor
import hung.deptrai.mycomic.core.presentation.components.getTagPriority
import hung.deptrai.mycomic.core.presentation.components.getTagTextColor

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
            .height(350.dp)

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
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.4f))
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent,
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
                .zIndex(0f)
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
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 6,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
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
            val matchedTags = manga.tags // không cần kiểm tra tagState nữa

            // Sắp xếp tag theo ưu tiên và tên
            val orderedTags = matchedTags.sortedWith(
                compareBy({ getTagPriority(it) }, { it.name })
            )

            Row(
                modifier = Modifier
                    .weight(1f)
                    .zIndex(2f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        fontWeight = FontWeight.Bold
                    )
                }

                // Sau đó là các tag
                orderedTags.take(4).forEach { tag ->
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
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
