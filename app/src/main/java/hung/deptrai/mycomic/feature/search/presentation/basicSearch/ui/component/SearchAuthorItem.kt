package hung.deptrai.mycomic.feature.search.presentation.basicSearch.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import hung.deptrai.mycomic.R
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.AuthorSearch

@Composable
fun SearchAuthorItem(
    authorSearch: AuthorSearch
) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(4.dp))
    ) {
        Card(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .height(60.dp),
            elevation = CardDefaults.cardElevation(4.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .border(BorderStroke(1.dp, Color.White), shape = CircleShape)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.avatar),
                            contentDescription = "avatar",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.matchParentSize()
                        )
                    }
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = authorSearch.name,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                Row {
                    Text(
                        text = if(authorSearch.mangaCount in 2..9){
                            authorSearch.mangaCount.toString() + " titles"
                        }else if(authorSearch.mangaCount>9){
                            "9+ titles"
                        }
                        else if(authorSearch.mangaCount == 1){
                            "1 title"
                        }
                        else{
                            "No titles"
                        },
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        }
    }
}