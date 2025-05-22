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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import hung.deptrai.mycomic.feature.explore_manga.domain.MangaHome
import hung.deptrai.mycomic.feature.explore_manga.presentation.HomePageAction
import hung.deptrai.mycomic.feature.explore_manga.presentation.HomeUiState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    action: (HomePageAction) -> Unit,
    homeUIState: HomeUiState
) {
    PullToRefreshLazyColumn(
        items = homeUIState.mangas,
        contents = { manga -> MangaCard(manga = manga, {}) },
        isRefresh = homeUIState.isRefreshing,
        onRefresh = { action(HomePageAction.PullToRefresh(true)) },
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> PullToRefreshLazyColumn(
    items: List<T>,
    contents: @Composable (T) -> Unit,
    isRefresh: Boolean,
    onRefresh:() -> Unit,
    modifier: Modifier,
    lazyListState: LazyListState = rememberLazyListState()
) {
    val pullToRefreshState = rememberPullToRefreshState()
    Box(
        modifier = modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)
    ){
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items.size){
                contents(items[it])
            }
        }

        if(pullToRefreshState.isRefreshing){
            LaunchedEffect(true){
                onRefresh()
            }
        }

        LaunchedEffect(isRefresh) {
            if(isRefresh){
                pullToRefreshState.startRefresh()
            } else {
                pullToRefreshState.endRefresh()
            }
        }

        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter),

        )
    }
}

@Composable
fun MangaItem(manga: MangaHome) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(manga.coverArt),
                contentDescription = manga.title,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = manga.title ?: "No Title", style = MaterialTheme.typography.titleMedium)
                Text(text = "Author: ${manga.authorName}", style = MaterialTheme.typography.bodySmall)
                Text(
                    text = "Tags: ${manga.tags.joinToString { it.name }}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}