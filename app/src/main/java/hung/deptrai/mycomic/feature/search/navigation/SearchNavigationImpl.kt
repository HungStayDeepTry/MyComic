package hung.deptrai.mycomic.feature.search.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import hung.deptrai.mycomic.core.navigation.FeatureNavigation
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.ui.SearchScreen
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel.SearchViewModel
import javax.inject.Inject

class SearchNavigationImpl @Inject constructor() : FeatureNavigation{
    override fun register(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable("search"){
            val viewModel = hiltViewModel<SearchViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            SearchScreen(
                action = {
                    viewModel.onEvent(it)
                },
                searchUIState = uiState
            )
        }
    }
}