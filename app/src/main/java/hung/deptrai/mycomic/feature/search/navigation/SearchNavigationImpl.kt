package hung.deptrai.mycomic.feature.search.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import hung.deptrai.mycomic.core.navigation.FeatureNavigation
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.ui.SearchScreen
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel.SearchViewModel
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel.TokenViewModel
import javax.inject.Inject

class SearchNavigationImpl @Inject constructor() : FeatureNavigation{
    override fun register(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable("search"){
            SearchScreen(
                tokenViewModel = hiltViewModel<TokenViewModel>(),
                searchViewModel = hiltViewModel<SearchViewModel>()
            )
        }
    }
}