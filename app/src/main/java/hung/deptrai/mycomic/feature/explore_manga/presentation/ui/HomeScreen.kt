package hung.deptrai.mycomic.feature.explore_manga.presentation.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
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
        modifier = Modifier.fillMaxSize(),
        content = {
            val popularManga = homeUIState.mangas.filter { it.customType == Type.POPULAR_NEW_TITLES }
            val latestManga = homeUIState.mangas.filter { it.customType == Type.LATEST_UPDATES }

            // Header for Popular
            item {
                if (popularManga.isNotEmpty()) {
                    Text(
                        text = "Popular New Titles",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            }

            item {
                if (popularManga.isNotEmpty()) {
                    PopularNewTitlesSection(popularManga)
                }
            }

            // Header for Latest
            item {
                if (latestManga.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Latest Updates")
                }
            }

            items(latestManga.size) { index ->
                MangaCard(manga = latestManga[index], {})
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
    lazyListState: LazyListState = rememberLazyListState(),
    content: LazyListScope.() -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()
    Box(
        modifier = modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
            contentPadding = PaddingValues(8.dp),
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
    var lastOffset by remember { mutableStateOf(0) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemScrollOffset }
            .collect { offset ->
                val delta = offset - lastOffset
                lastOffset = offset

                when {
                    delta > screenWidthPx / 4 && currentIndex < popularManga.lastIndex -> {
                        // Lướt sang phải quá 1/4, next 1 item
                        currentIndex++
                        scope.launch {
                            listState.animateScrollToItem(currentIndex)
                        }
                    }
                    delta < -screenWidthPx / 4 && currentIndex > 0 -> {
                        // Lướt sang trái quá 1/4, prev 1 item
                        currentIndex--
                        scope.launch {
                            listState.animateScrollToItem(currentIndex)
                        }
                    }
                    else -> {
                        // Lướt không quá 1/4, scroll lại item hiện tại
                        scope.launch {
                            listState.animateScrollToItem(currentIndex)
                        }
                    }
                }
            }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Popular New Titles",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )

        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(popularManga.size) { index ->
                PopularNewTitlesItem(popularManga[index]) {
                    onClickItem(popularManga[index])
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                if (currentIndex > 0) {
                    currentIndex--
                    scope.launch { listState.animateScrollToItem(currentIndex) }
                }
            }) {
                Text("Prev")
            }

            Button(onClick = {
                if (currentIndex < popularManga.lastIndex) {
                    currentIndex++
                    scope.launch { listState.animateScrollToItem(currentIndex) }
                }
            }) {
                Text("Next")
            }
        }
    }
}
