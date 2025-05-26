package hung.deptrai.mycomic.feature.explore_manga.presentation.ui

import PopularNewTitlesItem
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import hung.deptrai.mycomic.R
import hung.deptrai.mycomic.feature.explore_manga.domain.MangaHome
import hung.deptrai.mycomic.feature.explore_manga.domain.Type
import hung.deptrai.mycomic.feature.explore_manga.presentation.HomePageAction
import hung.deptrai.mycomic.feature.explore_manga.presentation.HomeUiState
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    action: (HomePageAction) -> Unit,
    homeUIState: HomeUiState
) {
    PullToRefreshScaffold(
        isRefresh = homeUIState.isRefreshing,
        onRefresh = { action(HomePageAction.PullToRefresh(true)) },
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        content = {
            val popularManga =
                homeUIState.mangas.filter { it.customType == Type.POPULAR_NEW_TITLES }
            val latestManga = homeUIState.mangas.filter { it.customType == Type.LATEST_UPDATES }

            // Header for Popular

            if (popularManga.isNotEmpty()) {
                PopularNewTitlesSection(popularManga)
            }

            // Header for Latest
            if (latestManga.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    Modifier.padding(start = 16.dp)
                ) {
                    Text(
                        text = "Latest Updates",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                latestManga.take(5).forEach { manga ->
                    MangaCard(manga = manga, { /* action */ })
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshScaffold(
    isRefresh: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
    content: @Composable ColumnScope.() -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()

    Box(
        modifier = modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = content
        )

        if (pullToRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                onRefresh()
            }
        }

        LaunchedEffect(isRefresh) {
            if (isRefresh) {
                pullToRefreshState.startRefresh()
            } else {
                pullToRefreshState.endRefresh()
            }
        }

        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun PopularNewTitlesSection(
    popularManga: List<MangaHome>,
    onClickItem: (MangaHome) -> Unit = {}
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val screenWidthPx = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }

    var currentIndex by remember { mutableIntStateOf(0) }

//    var lastIndex by remember { mutableStateOf(0) }
//    var lastOffset by remember { mutableStateOf(0) }

//    LaunchedEffect(listState) {
//        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
//            .collect { (index, offset) ->
//                val totalOffset = index * screenWidthPx + offset
//                val lastTotalOffset = lastIndex * screenWidthPx + lastOffset
//                val delta = totalOffset - lastTotalOffset
//
//                lastIndex = index
//                lastOffset = offset
//
//                when {
//                    delta > screenWidthPx / 4 && currentIndex < popularManga.lastIndex -> {
//                        currentIndex++
//                        scope.launch { listState.animateScrollToItem(currentIndex) }
//                    }
//                    delta < -screenWidthPx / 4 && currentIndex > 0 -> {
//                        currentIndex--
//                        scope.launch { listState.animateScrollToItem(currentIndex) }
//                    }
//                    else -> {
//                        scope.launch { listState.animateScrollToItem(currentIndex) }
//                    }
//                }
//            }
//    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp) // chiều cao fix cho ví dụ
    ) {
        Box(
            modifier = Modifier
                .weight(1f)        // chiếm hết phần còn lại trong Column
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyRow(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(popularManga.size) { index ->
                        PopularNewTitlesItem(popularManga[index]) {
                            onClickItem(popularManga[index])
                        }
                    }
                }
                Text(
                    text = "Popular New Titles",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .align(Alignment.TopStart) // Đặt text nằm trên cùng, bên trái
                        .padding(16.dp)
                        .background(Color.Black.copy(alpha = 0.5f), shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp) // padding trong background
                        .zIndex(1f) // đảm bảo text hiển thị trên LazyRow
                )
            }

            // Row nút "Next", "Prev" overlay đè lên LazyRow
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)  // nằm trên Box
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Nút Previous
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.DarkGray.copy(alpha = 0.7f))
                        .clickable {
                            if (currentIndex > 0) {
                                currentIndex--
                                scope.launch { listState.animateScrollToItem(currentIndex) }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_chevron_left_24),
                        contentDescription = "Previous",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "${currentIndex + 1}/10",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White
                )

                // Nút Next
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.DarkGray.copy(alpha = 0.7f))
                        .clickable {
                            if (currentIndex < popularManga.lastIndex) {
                                currentIndex++
                                scope.launch { listState.animateScrollToItem(currentIndex) }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_chevron_right_24),
                        contentDescription = "Next",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

//        Spacer(modifier = Modifier.height(18.dp))

        // Các thành phần khác trong Column nếu có
    }

}
