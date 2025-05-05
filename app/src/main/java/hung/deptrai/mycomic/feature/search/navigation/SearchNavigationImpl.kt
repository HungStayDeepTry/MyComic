package hung.deptrai.mycomic.feature.search.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import hung.deptrai.mycomic.core.navigation.FeatureNavigation
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.ui.SearchScreen
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel.AuthorSearchViewModel
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel.ComicSearchViewModel
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel.ScanlationGroupSearchViewModel
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel.TagSearchViewModel
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel.TokenViewModel
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel.UserSearchViewModel
import javax.inject.Inject

class SearchNavigationImpl @Inject constructor() : FeatureNavigation{
    override fun register(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable("search"){
            SearchScreen(
                comicViewModel = hiltViewModel<ComicSearchViewModel>(),
                scanlationGroupSearchViewModel = hiltViewModel<ScanlationGroupSearchViewModel>(),
                authorViewModel = hiltViewModel<AuthorSearchViewModel>(),
                tokenViewModel = hiltViewModel<TokenViewModel>(),
                userSearchViewModel = hiltViewModel<UserSearchViewModel>(),
                tagViewModel = hiltViewModel<TagSearchViewModel>()
            )
        }
    }
}