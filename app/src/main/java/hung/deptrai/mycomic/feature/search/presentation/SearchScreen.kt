package hung.deptrai.mycomic.feature.search.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val searchState by viewModel.searchState.collectAsState()

    // Đầu vào tìm kiếm
    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Search bar
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search Manga") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Button to trigger search
        Button(
            onClick = { viewModel.searchComic(query) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (searchState) {
            is Result.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is Result.Success -> {
                val searchResults = (searchState as Result.Success).data
                SearchResultList(searchResults)
            }
            is Result.Error -> {
                val errorMessage = (searchState as Result.Error).exception.message ?: "Unknown error"
                Text(errorMessage, color = MaterialTheme.colorScheme.onError)
            }
        }
    }
}

@Composable
fun SearchResultList(searchResults: List<SearchComic>) {
    LazyColumn {
        items(searchResults) { comic ->
            SearchResultItem(comic)
        }
    }
}

@Composable
fun SearchResultItem(comic: SearchComic) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = comic.title, style = MaterialTheme.typography.titleLarge)
            Text(text = comic.description, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Status: ${comic.status}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
