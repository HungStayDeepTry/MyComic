package hung.deptrai.mycomic.feature.explore_manga.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import hung.deptrai.mycomic.core.navigation.FeatureNavigation
import hung.deptrai.mycomic.feature.explore_manga.presentation.HomeViewModel
import hung.deptrai.mycomic.feature.explore_manga.presentation.ui.HomeScreen
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.ui.SearchScreen
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel.SearchViewModel
import javax.inject.Inject

class HomeNavigation @Inject constructor() : FeatureNavigation {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun register(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable("home"){
            val viewModel = hiltViewModel<HomeViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            HomeScreen(
                action = {
                    viewModel.onEvent(it)
                },
                homeUIState = uiState
            )
        }
    }
}