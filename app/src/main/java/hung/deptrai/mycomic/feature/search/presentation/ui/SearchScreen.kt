package hung.deptrai.mycomic.feature.search.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import hung.deptrai.mycomic.feature.search.presentation.AuthorSearch
import hung.deptrai.mycomic.feature.search.presentation.Result
import hung.deptrai.mycomic.feature.search.presentation.ScanlationGroupSearch
import hung.deptrai.mycomic.feature.search.presentation.SearchComic
import hung.deptrai.mycomic.feature.search.presentation.viewmodel.SearchViewModel
import hung.deptrai.mycomic.feature.search.presentation.ui.component.MangaSearchResultItem
import hung.deptrai.mycomic.feature.search.presentation.ui.component.ScanlationGroupSearchItem
import hung.deptrai.mycomic.feature.search.presentation.ui.component.SearchAuthorItem
import hung.deptrai.mycomic.feature.search.presentation.viewmodel.ScanlationGroupSearchViewModel
import hung.deptrai.mycomic.feature.search.presentation.viewmodel.TokenViewModel
import hung.deptrai.mycomic.feature.search.presentation.viewmodel.UserSearchViewModel

@Composable
fun SearchScreen(
    comicViewModel: SearchViewModel<SearchComic>,
    authorViewModel: SearchViewModel<AuthorSearch>,
    scanlationGroupSearchViewModel: SearchViewModel<ScanlationGroupSearch>,
    userSearchViewModel: UserSearchViewModel,
    tokenViewModel: TokenViewModel
) {
    val comicSearchState by comicViewModel.searchState.collectAsState()
    val authorSearchState by authorViewModel.searchState.collectAsState()
//    val pr = LocalViewModelStoreOwner.current
    val scanlationGroupSearchState by scanlationGroupSearchViewModel.searchState.collectAsState()
    val tokenState by tokenViewModel.tokenState.collectAsState()
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
//                viewModel.searchComic(it)
                comicViewModel.search(it)
                authorViewModel.search(it)
                scanlationGroupSearchViewModel.search(it)
                tokenViewModel.readToken()
            })
        }

        item {
            SearchTabs(
                selectedTabIndex = selectedTabIndex.value,
                onTabSelected = { selectedTabIndex.value = it }
            )
        }

        item {
            TabContent(
                comicSearchState = comicSearchState,
                authorSearchState = authorSearchState,
                scanlationGroupSearchState = scanlationGroupSearchState,
                selectedTabIndex = selectedTabIndex.value,
                userSearchViewModel = userSearchViewModel,
                token = tokenState,
                query = query
            )
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
fun TabContent(
    comicSearchState: Result<List<SearchComic>>,
    authorSearchState: Result<List<AuthorSearch>>,
    scanlationGroupSearchState: Result<List<ScanlationGroupSearch>>,
    token: String,
    userSearchViewModel: UserSearchViewModel,
    selectedTabIndex: Int,
    query: String
) {
    val currentState = when (selectedTabIndex) {
        0, 1 -> comicSearchState // "All" và "Manga" cùng dùng comicSearchState
        2 -> authorSearchState   // "Author" dùng authorSearchState
        3 -> scanlationGroupSearchState // "Group" dùng scanlationGroupSearchState
        else -> comicSearchState
    }

    when (currentState) {
        is Result.Loading -> {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        is Result.Success -> {
            val searchResults = currentState.data
            AnimatedContent(targetState = selectedTabIndex, label = "") {
                when (selectedTabIndex) {
                    0, 1 -> { // Manga list

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            searchResults.filterIsInstance<SearchComic>().forEach { comic ->
                                MangaSearchResultItem(comic) {}
                            }
                        }
                    }
                    2 -> { // Author list
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            searchResults.filterIsInstance<AuthorSearch>().forEach { author ->
                                SearchAuthorItem(author)
                            }
                        }
                    }
                    3 -> { // Group list
                        val leaderIdsList = searchResults.filterIsInstance<ScanlationGroupSearch>()
                        val leaderIds = leaderIdsList.flatMap { it.leaderName ?: emptyList() }
                        val users = userSearchViewModel.searchState.collectAsState().value
// Gửi yêu cầu lấy users
                        LaunchedEffect(leaderIds) {
                            if (leaderIds.isNotEmpty()) {
                                userSearchViewModel.getUsers(token = token, ids = leaderIds)
                            }
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            leaderIdsList.forEach { group ->
                                Column {
//                                    Text(group.name)
//
                                    val matchedUsers = group.leaderName
                                        ?.mapNotNull { leaderId ->
                                            // Tìm user có id trùng với leaderId
                                            (users as? Result.Success)?.data
                                                ?.firstOrNull { it.id == leaderId }
                                        }
                                        ?: emptyList()
//
//                                    matchedUsers.forEach { user ->
//                                        Text(text = user.name)
//                                    }
                                    ScanlationGroupSearchItem(group, matchedUsers)
                                }
                            }
                        }
                    }
                }
            }
        }
        is Result.Error -> {
            val errorMessage = currentState.exception.message ?: "Unknown error"
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
@Preview
@Composable
private fun Prev1() {
//    SearchScreen(viewModel = hiltViewModel<SearchViewModel>())
}

