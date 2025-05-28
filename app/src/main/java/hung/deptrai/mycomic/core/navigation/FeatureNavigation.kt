package hung.deptrai.mycomic.core.navigation

import androidx.compose.foundation.ScrollState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface FeatureNavigation {
    fun register(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        onScrollStateChanged: (ScrollState)  -> Unit
    )
}