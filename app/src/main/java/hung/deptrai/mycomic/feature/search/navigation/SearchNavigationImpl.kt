package hung.deptrai.mycomic.feature.search.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import hung.deptrai.mycomic.core.navigation.FeatureNavigation
import hung.deptrai.mycomic.feature.search.presentation.AuthorSearch
import hung.deptrai.mycomic.feature.search.presentation.ScanlationGroupSearch
import hung.deptrai.mycomic.feature.search.presentation.SearchComic
import hung.deptrai.mycomic.feature.search.presentation.ui.SearchScreen
import hung.deptrai.mycomic.feature.search.presentation.viewmodel.AuthorSearchViewModel
import hung.deptrai.mycomic.feature.search.presentation.viewmodel.ComicSearchViewModel
import hung.deptrai.mycomic.feature.search.presentation.viewmodel.ScanlationGroupSearchViewModel
import hung.deptrai.mycomic.feature.search.presentation.viewmodel.SearchViewModel
import javax.inject.Inject

class SearchNavigationImpl @Inject constructor() : FeatureNavigation{
    override fun register(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable("search"){
            SearchScreen(
                comicViewModel = hiltViewModel<ComicSearchViewModel>(),
                scanlationGroupSearchViewModel = hiltViewModel<ScanlationGroupSearchViewModel>(),
                authorViewModel = hiltViewModel<AuthorSearchViewModel>()
            )
        }
    }
}