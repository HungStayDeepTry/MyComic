package hung.deptrai.mycomic.feature.search.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import hung.deptrai.mycomic.R
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import hung.deptrai.mycomic.feature.search.presentation.Result
import hung.deptrai.mycomic.feature.search.presentation.SearchComic
import hung.deptrai.mycomic.feature.search.presentation.SearchViewModel
import hung.deptrai.mycomic.feature.search.presentation.ui.component.MangaSearchResultItem

@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val searchState by viewModel.searchState.collectAsState()
    var query by remember { mutableStateOf("") }
    val selectedTabIndex = remember { mutableStateOf(0) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SearchBar(query = query, onQueryChange = {
                query = it
                viewModel.searchComic(it)
            })
        }

        item {
            SearchTabs(
                selectedTabIndex = selectedTabIndex.value,
                onTabSelected = { selectedTabIndex.value = it }
            )
        }

        item {
            TabContent(searchState = searchState, selectedTabIndex.value)
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp)
            .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxSize(),
            singleLine = true,
            textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.search_ic),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (query.isEmpty()) {
                        Text(
                            text = "Search Manga",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}
@Composable
fun SearchTabs(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    val offsetAnim = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(selectedTabIndex) {
        offsetAnim.snapTo(0f)
        scope.launch {
            offsetAnim.animateTo(targetValue = -5f, animationSpec = tween(durationMillis = 50))
            offsetAnim.animateTo(targetValue = 5f, animationSpec = tween(durationMillis = 50))
            offsetAnim.animateTo(targetValue = 0f, animationSpec = tween(durationMillis = 50))
        }
    }

    TabRow(
        selectedTabIndex = selectedTabIndex,
        indicator = { tabPositions ->
            SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = MaterialTheme.colorScheme.primary
            )
        }
    ) {
        listOf("All", "Manga", "Author", "Group").forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = { Text(title) },
                modifier = Modifier.offset(x = if (selectedTabIndex == index) offsetAnim.value.dp else 0.dp)
            )
        }
    }
}
@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun TabContent(searchState: Result<List<SearchComic>>, selectedTabIndex: Int) {
    AnimatedContent(targetState = searchState, label = "") {
        when (searchState) {
            is Result.Loading -> {
                Column(
                    Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
            is Result.Success -> {
                val searchResults = searchState.data
                Column(
                    Modifier.wrapContentSize()
                ) {
//                    searchResults.forEach { comic ->
//                        MangaSearchResultItem(comic) {}
//                    }
                    AnimatedContent(targetState = selectedTabIndex, label = "") {
                        when (selectedTabIndex) {
                            0 -> {
                                Column(
                                    modifier = Modifier.fillMaxSize()
                                ) {

                                }
                                // Add your content for "All" here
                            }
                            1 -> {
                                Column {
                                    searchResults.forEach { it ->
                                        MangaSearchResultItem(it) {}
                                    }
                                }
                                // Add your content for "Manga" here
                            }
                            2 -> {
                                Column(
                                    modifier = Modifier.fillMaxSize()
                                ) {

                                }
                                // Add your content for "Author" here
                            }
                            3 -> {
                                Column(
                                    modifier = Modifier.fillMaxSize()
                                ) {

                                }
                                // Add your content for "Group" here
                            }
                        }
                    }
                }
            }
            is Result.Error -> {
                val errorMessage = searchState.exception.message ?: "Unknown error"
                Text(text = errorMessage, color = MaterialTheme.colorScheme.onError)
            }
        }
    }
}
@Preview
@Composable
private fun Prev1() {
    SearchScreen(viewModel = hiltViewModel<SearchViewModel>())
}

