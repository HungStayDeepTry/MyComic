//package hung.deptrai.mycomic.feature.search.presentation.ui.component
//
//import android.annotation.SuppressLint
//import androidx.compose.animation.AnimatedContent
//import androidx.compose.runtime.Composable
//import androidx.compose.animation.core.Animatable
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.selection.selectableGroup
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.Alignment
//import androidx.compose.animation.core.FastOutSlowInEasing
//import androidx.compose.animation.core.spring
//import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
//import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
//import hung.deptrai.mycomic.feature.search.presentation.Result
//import hung.deptrai.mycomic.feature.search.presentation.SearchComic
//import kotlinx.coroutines.launch
//
//@SuppressLint("UnusedContentLambdaTargetStateParameter")
//@Composable
//fun TabRowExample(
//    searchState: Result<List<SearchComic>>
//) {
//    // State để theo dõi tab hiện tại
//    val selectedTabIndex = remember { mutableStateOf(0) }
//
//    // Animatable để làm rung cho Tab
//    val offsetAnim = remember { Animatable(0f) }
//
//    // Coroutine scope cho LaunchedEffect
//    val scope = rememberCoroutineScope()
//
//    // Khi tab được chọn, thực hiện hiệu ứng rung
//    LaunchedEffect(selectedTabIndex.value) {
//        // Reset về 0 sau khi đã hoàn thành hiệu ứng
//        offsetAnim.snapTo(0f)
//
//        // Làm rung tab
//        scope.launch {
//            offsetAnim.animateTo(
//                targetValue = -5f,
//                animationSpec = tween(durationMillis = 50)
//            )
//            // Di chuyển sang phải trong 0.1s
//            offsetAnim.animateTo(
//                targetValue = 5f,
//                animationSpec = tween(durationMillis = 50)
//            )
//            // Quay lại vị trí ban đầu trong 0.1s
//            offsetAnim.animateTo(
//                targetValue = 0f,
//                animationSpec = tween(durationMillis = 50)
//            )
//        }
//    }
//
//    Column {
//        // TabRow chứa các Tab
//        TabRow(
//            selectedTabIndex = selectedTabIndex.value,
//            indicator = { tabPositions ->
//                // Tạo chỉ báo dưới các tab
//                SecondaryIndicator(
//                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex.value]),
//                    color = MaterialTheme.colorScheme.primary
//                )
//            }
//        ) {
//            // Các tab trong TabRow
//            Tab(
//                selected = selectedTabIndex.value == 0,
//                onClick = { selectedTabIndex.value = 0 },
//                text = { Text("All") },
//                modifier = Modifier.offset(x = if (selectedTabIndex.value == 0) offsetAnim.value.dp else 0.dp)
//            )
//            Tab(
//                selected = selectedTabIndex.value == 1,
//                onClick = { selectedTabIndex.value = 1 },
//                text = { Text("Manga") },
//                modifier = Modifier.offset(x = if (selectedTabIndex.value == 1) offsetAnim.value.dp else 0.dp)
//            )
//            Tab(
//                selected = selectedTabIndex.value == 2,
//                onClick = { selectedTabIndex.value = 2 },
//                text = { Text("Author") },
//                modifier = Modifier.offset(x = if (selectedTabIndex.value == 2) offsetAnim.value.dp else 0.dp)
//            )
//            Tab(
//                selected = selectedTabIndex.value == 3,
//                onClick = { selectedTabIndex.value = 3 },
//                text = { Text("Group") },
//                modifier = Modifier.offset(x = if (selectedTabIndex.value == 3) offsetAnim.value.dp else 0.dp)
//            )
//        }
//
//        // Nội dung cho từng tab
//        AnimatedContent(targetState = selectedTabIndex, label = "") {
//            when (selectedTabIndex.value) {
//                0 -> {
//                    Column(
//                        modifier = Modifier.fillMaxSize()
//                    ) {
//
//                    }
//                    // Add your content for "All" here
//                }
//                1 -> {
//                    SearchComicListItem(searchState = searchState)
//                    // Add your content for "Manga" here
//                }
//                2 -> {
//                    Column(
//                        modifier = Modifier.fillMaxSize()
//                    ) {
//
//                    }
//                    // Add your content for "Author" here
//                }
//                3 -> {
//                    Column(
//                        modifier = Modifier.fillMaxSize()
//                    ) {
//
//                    }
//                    // Add your content for "Group" here
//                }
//            }
//        }
//    }
//}