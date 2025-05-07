package hung.deptrai.mycomic.feature.search.presentation.basicSearch.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hung.deptrai.mycomic.R
import hung.deptrai.mycomic.feature.search.domain.SearchType
import hung.deptrai.mycomic.feature.search.domain.model.AuthorSearch
import hung.deptrai.mycomic.feature.search.domain.model.ScanlationGroupSearch
import hung.deptrai.mycomic.feature.search.domain.model.SearchComic
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.ui.component.MangaSearchResultItem
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.ui.component.ScanlationGroupSearchItem
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.ui.component.SearchAuthorItem
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel.SearchEvent
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel.SearchViewModel
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel.TokenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    tokenViewModel: TokenViewModel
) {
    val comicSearchState by searchViewModel.comics.collectAsState()
    val authorSearchState by searchViewModel.authors.collectAsState()
    val scanlationGroupSearchState by searchViewModel.groups.collectAsState()
    val tokenState by tokenViewModel.tokenState.collectAsState()
    val searchStatus by searchViewModel.events.collectAsState(initial = null)

    var textInput by rememberSaveable { mutableStateOf("") }
    val textInput2 by searchViewModel.inputText.collectAsState()
//    var query by rememberSaveable { mutableStateOf("") }
    val selectedTabIndex = rememberSaveable { mutableStateOf(0) }
    var typeInput by rememberSaveable { mutableStateOf(SearchType.ALL) }

    // Debounce query sau 500ms
    LaunchedEffect(textInput) {
        delay(500)
//        query = textInput

//        searchViewModel.textChanged(textInput)

        // Chỉ khi query thực sự thay đổi sau delay mới search
        searchViewModel.search(textInput2, typeInput)
        tokenViewModel.readToken()
    }

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 8.dp)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                SearchBar(query = textInput2, onQueryChange = {
                    searchViewModel.textChanged(it)
                })
            }

            item {
                SearchTabs(
                    selectedTabIndex = selectedTabIndex.value,
                    onTabSelected = { selectedTabIndex.value = it }
                )
            }

            item {
                searchStatus?.let {
                    TabContent(
                        comicSearchState = comicSearchState,
                        authorSearchState = authorSearchState,
                        scanlationGroupSearchState = scanlationGroupSearchState,
                        selectedTabIndex = selectedTabIndex.value,
                        token = tokenState,
                        status = it
                    )
                }
            }
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
            Box(
                Modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .height(3.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )
        },
        divider = {}, // Loại bỏ đường kẻ phân cách dưới TabRow
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        listOf("All", "Manga", "Author", "Group").forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = { Text(title) },
                modifier = Modifier
                    .offset(x = if (selectedTabIndex == index) offsetAnim.value.dp else 0.dp)
//                    .background(
//                        if (selectedTabIndex == index) Color.Blue
//                        else Color.Transparent
//                    )
            )
        }
    }
}

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun TabContent(
    comicSearchState: List<SearchComic>,
    authorSearchState: List<AuthorSearch>,
    scanlationGroupSearchState: List<ScanlationGroupSearch>,
    token: String,
    status: SearchEvent,
    selectedTabIndex: Int
) {
    when (status) {
        is SearchEvent.Loading -> {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }

        is SearchEvent.Success -> {
            AnimatedContent(targetState = selectedTabIndex, label = "") {
                when (selectedTabIndex) {
                    0, 1 -> { // Manga list
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Manga",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(8.dp)
                            )
                            comicSearchState.forEach { comic ->
                                MangaSearchResultItem(
                                    comic
                                ) {}
                            }
                        }
                    }

                    2 -> { // Author list
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Author",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(8.dp)
                            )
                            authorSearchState.forEach { author ->
                                SearchAuthorItem(author)
                            }
                        }
                    }

                    3 -> { // Group list
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Group",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(8.dp)
                            )
                            scanlationGroupSearchState.forEach { group ->
                                Column {
                                    ScanlationGroupSearchItem(group)
                                }
                            }
                        }
                    }
                }
            }
        }

        is SearchEvent.Error -> {
            val errorMessage = status.message.asString()
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }

        SearchEvent.Empty -> TODO()
    }
}

@Preview
@Composable
private fun Prev1() {
//    SearchScreen(viewModel = hiltViewModel<SearchViewModel>())
}

