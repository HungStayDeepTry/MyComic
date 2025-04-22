package hung.deptrai.mycomic.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import hung.deptrai.mycomic.core.navigation.FeatureNavigation
import javax.inject.Inject

class SearchNavigationImpl @Inject constructor() : FeatureNavigation{
    override fun register(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable("search"){

        }
    }
}