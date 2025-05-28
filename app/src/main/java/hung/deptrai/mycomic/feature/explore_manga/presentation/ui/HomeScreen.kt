package hung.deptrai.mycomic.feature.explore_manga.presentation.ui

import PopularNewTitlesItem
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
    homeUIState: HomeUiState,
    onScrollStateChanged: (ScrollState) -> Unit
) {
    val scrollState = rememberScrollState()
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.value }
            .collect { index ->
                onScrollStateChanged(scrollState)
            }
    }
    PullToRefreshScaffold(
        isRefresh = homeUIState.isRefreshing,
        onRefresh = { action(HomePageAction.PullToRefresh(true)) },
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        scrollState = scrollState,
        content = {
            val popularManga =
                homeUIState.mangas.filter { it.customType == Type.POPULAR_NEW_TITLES }
            val latestManga = homeUIState.mangas.filter { it.customType == Type.LATEST_UPDATES }
            val staffPick = homeUIState.mangas.filter {
                it.customType == Type.STAFF_PICKS
            }
            val feature = homeUIState.mangas.filter {
                it.customType == Type.FEATURE
            }
            val seasonal = homeUIState.mangas.filter {
                it.customType == Type.SEASONAL
            }
            val recentlyAdded = homeUIState.mangas.filter {
                it.customType == Type.RECENTLY_ADDED
            }
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
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
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
            if(staffPick.isNotEmpty()){
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    Modifier.padding(start = 16.dp)
                ) {
                    Text(
                        text = "Staff Picks",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Row (
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth()
                    .padding(start = 8.dp)
                    .horizontalScroll(rememberScrollState())
            ){
                staffPick.take(10).forEach {
                    CustomListItem(it) {

                    }
                }
            }

            if(feature.isNotEmpty()){
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    Modifier.padding(start = 16.dp)
                ) {
                    Text(
                        text = "Feature By Supporters",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Row (
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth()
                    .padding(start = 8.dp)
                    .horizontalScroll(rememberScrollState())
            ){
                feature.take(10).forEach {
                    CustomListItem(it) {

                    }
                }
            }

            if(seasonal.isNotEmpty()){
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    Modifier.padding(start = 16.dp)
                ) {
                    Text(
                        text = "Seasonal: Winter 2025",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Row (
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth()
                    .padding(start = 8.dp)
                    .horizontalScroll(rememberScrollState())
            ){
                seasonal.take(10).forEach {
                    CustomListItem(it) {

                    }
                }
            }
            if(recentlyAdded.isNotEmpty()){
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    Modifier.padding(start = 16.dp)
                ) {
                    Text(
                        text = "Recently Added",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Row (
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth()
                    .padding(start = 8.dp)
                    .horizontalScroll(rememberScrollState())
            ){
                recentlyAdded.take(10).forEach {
                    CustomListItem(it) {

                    }
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
    scrollState: ScrollState,
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

    var currentIndex by remember { mutableIntStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(440.dp) // chiều cao fix cho ví dụ
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
                        .fillMaxSize()
                        .align(Alignment.Center),
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
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.TopStart) // Đặt text nằm trên cùng, bên trái
                        .padding(16.dp)
                        .padding(vertical = 85.dp) // padding trong background
                        .zIndex(1f) // đảm bảo text hiển thị trên LazyRow
                )
            }

            // Row nút "Next", "Prev" overlay đè lên LazyRow
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)  // nằm trên Box
                    .padding(horizontal = 8.dp),
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
                    color = MaterialTheme.colorScheme.onBackground
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
    }

}
