package hung.deptrai.mycomic.feature.search.presentation.ui.component

import android.graphics.Shader
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hung.deptrai.mycomic.R
import hung.deptrai.mycomic.feature.search.presentation.ScanlationGroupSearch
import hung.deptrai.mycomic.feature.search.presentation.UserSearch

@Composable
fun ScanlationGroupSearchItem(
    scanlationGroupSearch: ScanlationGroupSearch,
    userSearchs: List<UserSearch>
) {
    val gradientGroupBanner = Brush.horizontalGradient(
        colors = listOf(
            Color.Black,
            Color.Black,
            Color.White
        )
    )
    Box(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(4.dp))
    ) {
        Card(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.fillMaxWidth().height(60.dp),
            elevation = CardDefaults.cardElevation(4.dp),
        ) {
            Box {
                Image(
                    painter = painterResource( R.drawable.groupbanner),  // Path to your image
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .height(60.dp)
                        .background(gradientGroupBanner)
                        .graphicsLayer {
                            alpha = 0.4f // Độ trong suốt (opacity)
                            // Áp dụng hiệu ứng mờ
                            shadowElevation = 10f // Tạo bóng
                        }
                )
                Row(
                    Modifier
                        .height(60.dp)
                        .padding(start = 8.dp), // cho hình cách mép 1 tí đẹp hơn
                    verticalAlignment = Alignment.CenterVertically, // cho hình nằm giữa
                    horizontalArrangement = Arrangement.Start
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
                    Column(
                        Modifier.height(45.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = scanlationGroupSearch.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row {
                            val leaderIds = scanlationGroupSearch.leaderName.orEmpty() // list id của leader
                            val leaderNames = userSearchs
                                .filter { it.id in leaderIds } // lọc ra những UserSearch có id nằm trong leaderName
                                .map { it.name } // lấy tên của họ

                            val hasMatchingLeader = leaderNames.isNotEmpty()

                            Row {
                                Text(
                                    text = if (!hasMatchingLeader) "No leader" else "Leader: ",
                                    color = Color.White
                                )
                                if (hasMatchingLeader) {
                                    Text(
                                        text = leaderNames.joinToString(", "), // ghép các tên thành chuỗi cách nhau bằng dấu ", "
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Prev3() {
//    ScanlationGroupSearchItem(ScanlationGroupSearch("1", "hungTeam", listOf("hung", "hung2"), emptyList(), false, false))
}