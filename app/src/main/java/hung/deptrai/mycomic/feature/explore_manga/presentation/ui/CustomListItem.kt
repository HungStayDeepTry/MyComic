package hung.deptrai.mycomic.feature.explore_manga.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import hung.deptrai.mycomic.R
import hung.deptrai.mycomic.core.utils.MdLang
import hung.deptrai.mycomic.feature.explore_manga.domain.MangaHome

@Composable
fun CustomListItem(
    manga: MangaHome,
    action: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(130.dp)
            .clickable { action() }
            .padding(4.dp)
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .width(130.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(manga.coverArt),
                    contentDescription = "Manga Cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )

                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(
                        MdLang.fromIsoCode(
                            manga.originalLang ?: ""
                        )?.iconResId ?: R.drawable.ic_flag_vn
                    ),
                    contentDescription = MdLang.fromIsoCode(
                        manga.originalLang ?: ""
                    )?.lang,
                    tint = Color.Unspecified
                )
            }
        }

        manga.title?.let {
            Text(
                text = it,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}