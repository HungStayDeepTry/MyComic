package hung.deptrai.mycomic.feature.search.presentation.basicSearch.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.ui.component.SeeMoreButton
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel.SearchEvent
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel
) {
    val context = LocalContext.current
    val comicSearchState by searchViewModel.comics.collectAsState()
    val authorSearchState by searchViewModel.authors.collectAsState()
    val scanlationGroupSearchState by searchViewModel.groups.collectAsState()
    val searchStatus by searchViewModel.events.collectAsState(initial = null)
    val errorEvents by searchViewModel.errorEvent.collectAsState(initial = null)
    val textInput2 by searchViewModel.inputText.collectAsState()
    val selectedTabIndex = rememberSaveable { mutableStateOf(0) }
    val errorMessage = when (errorEvents) {
        is SearchEvent.Error -> (errorEvents as SearchEvent.Error).message.asString()
        is SearchEvent.ErrorAuthor -> "Author error"
        is SearchEvent.ErrorComic -> "Comic error"
        is SearchEvent.ErrorGroup -> "Group error"
        else -> null
    }
    LaunchedEffect(errorEvents) {
        if (errorMessage != null) {
            // xử lý thông báo lỗi, ví dụ: hiển thị snackbar
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    val focusManager = LocalFocusManager.current

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    focusManager.clearFocus() // ✅ clear focus khi click ra ngoài
                }
        ) {
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
                        onTabSelected = { selectedTabIndex.value = it },
                        searchViewModel = searchViewModel
                    )
                }

                item {
                    searchStatus?.let {
                        TabContent(
                            comicSearchState = comicSearchState,
                            authorSearchState = authorSearchState,
                            scanlationGroupSearchState = scanlationGroupSearchState,
                            selectedTabIndex = selectedTabIndex.value,
                            status = it
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    var isFocused by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val focusModifier = Modifier
        .onFocusChanged {
            isFocused = it.isFocused
        }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp)
            .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp))
            .border(
                width = if (isFocused) 2.dp else 0.dp,
                color = if (isFocused) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = focusModifier
                .fillMaxSize()
                .focusRequester(focusRequester),
            singleLine = true,
            textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxSize()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 8.dp) // chừa chỗ cho trailing icon
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
                                text = "Search",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }

                        innerTextField()
                    }

                    if (query.isNotEmpty()) {
                        Box(
                            Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(MaterialTheme.colorScheme.primary)
                                .align(Alignment.BottomEnd),
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(
                                onClick = {
                                    onQueryChange("")
                                },
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_close_24),
                                    contentDescription = "Clear",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun SearchTabs(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    searchViewModel: SearchViewModel
) {
    val offsetAnim = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val textInput2 by searchViewModel.inputText.collectAsState()

    LaunchedEffect(selectedTabIndex) {
        offsetAnim.snapTo(0f)
        scope.launch {
            offsetAnim.animateTo(targetValue = -5f, animationSpec = tween(durationMillis = 50))
            offsetAnim.animateTo(targetValue = 5f, animationSpec = tween(durationMillis = 50))
            offsetAnim.animateTo(targetValue = 0f, animationSpec = tween(durationMillis = 50))
        }
    }

    TabRow(
        selectedTabIndex = selectedTabIndex, // Loại bỏ đường kẻ phân cách dưới TabRow
        containerColor = MaterialTheme.colorScheme.outlineVariant,
        modifier = Modifier
            .height(40.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable {
//                searchViewModel.search(textInput2, type)
            },
        contentColor = MaterialTheme.colorScheme.outlineVariant,
        indicator = {}
    ) {
        listOf(
            SearchType.ALL,
            SearchType.COMIC,
            SearchType.AUTHOR,
            SearchType.GROUP
        ).forEachIndexed { index, title ->
            if (selectedTabIndex == index) {
                searchViewModel.setTypeInput(title)
//                searchViewModel.search(textInput2, title)
                Tab(
                    selected = true,
                    onClick = {
                        onTabSelected(index)
//                        searchViewModel.search(textInput2, title)
                    },
                    text = {
                        Text(
                            text = title.toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    modifier = Modifier
                        .height(40.dp)
                        .offset(x = offsetAnim.value.dp)
                        .padding(3.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            MaterialTheme.colorScheme.scrim
                        )
                )
            } else {
                Tab(
                    selected = false,
                    onClick = {
                        onTabSelected(index)
                        searchViewModel.search(textInput2, title)
                    },
                    text = {
                        Text(
                            text = title.toString(),
                            color = MaterialTheme.colorScheme.outline,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    modifier = Modifier
                        .height(40.dp)
                        .background(
                            MaterialTheme.colorScheme.outlineVariant
                        )
                        .padding(3.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
            }
        }
    }
}

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun TabContent(
    comicSearchState: List<SearchComic>,
    authorSearchState: List<AuthorSearch>,
    scanlationGroupSearchState: List<ScanlationGroupSearch>,
//    token: String,
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
                    0 -> {
                        Column {
                            MangaSection(comicSearchState)
                            Spacer(Modifier.height(16.dp))
                            AuthorSection(authorSearchState)
                            Spacer(Modifier.height(16.dp))
                            ScanlationGroupSection(scanlationGroupSearchState)
                        }
                    }

                    1 -> { // Manga list
                        MangaSection(comicSearchState)
                    }

                    2 -> { // Author list
                        AuthorSection(authorSearchState)
                    }

                    3 -> { // Group list
                        ScanlationGroupSection(scanlationGroupSearchState)
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

        is SearchEvent.ErrorAuthor -> {
            val errorMessage = status.message.asString()
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }

        is SearchEvent.ErrorComic -> {
            val errorMessage = status.message.asString()
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }

        is SearchEvent.ErrorGroup -> {
            val errorMessage = status.message.asString()
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

@Composable
private fun ScanlationGroupSection(scanlationGroupSearchState: List<ScanlationGroupSearch>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (scanlationGroupSearchState.isNotEmpty()) {
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
            SeeMoreButton(SearchType.GROUP)
        }
    }
}

@Composable
private fun AuthorSection(authorSearchState: List<AuthorSearch>) {
    if (authorSearchState.isNotEmpty()) {
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
            SeeMoreButton(SearchType.AUTHOR)
        }
    }
}

@Composable
private fun MangaSection(comicSearchState: List<SearchComic>) {
    if (comicSearchState.isNotEmpty()) {
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
            SeeMoreButton(SearchType.COMIC)
        }
    }
}

@Preview
@Composable
private fun Prev1() {
//    SearchScreen(viewModel = hiltViewModel<SearchViewModel>())
}

