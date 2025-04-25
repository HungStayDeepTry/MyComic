package hung.deptrai.mycomic.feature.search.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import hung.deptrai.mycomic.feature.search.presentation.Result
import hung.deptrai.mycomic.feature.search.presentation.SearchComic

@Composable
fun SearchComicListItem(
    searchState: Result<List<SearchComic>>
) {
    LazyColumn {
        when (searchState) {
            is Result.Loading -> {
                item {
                    Column {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                }
            }

            is Result.Success -> {
                val searchResults = (searchState as Result.Success).data
                items(searchResults) { comic ->
                    MangaSearchResultItem(comic) {}
                }
            }

            is Result.Error -> {
                val errorMessage =
                    (searchState as Result.Error).exception.message ?: "Unknown error"
                item {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.onError
                    )
                }
            }
        }
    }
}