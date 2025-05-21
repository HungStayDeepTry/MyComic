package hung.deptrai.mycomic.feature.explore_manga.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import hung.deptrai.mycomic.feature.explore_manga.domain.MangaHome
import hung.deptrai.mycomic.feature.explore_manga.presentation.HomePageAction
import hung.deptrai.mycomic.feature.explore_manga.presentation.HomeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    action: (HomePageAction) -> Unit,
    homeUIState: HomeUiState
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = homeUIState.isRefreshing,
        onRefresh = { action(HomePageAction.PullToRefresh(true)) }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(homeUIState.mangas) { manga ->
                MangaItem(manga = manga)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        PullRefreshIndicator(
            refreshing = homeUIState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
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