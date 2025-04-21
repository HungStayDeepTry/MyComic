package hung.deptrai.mycomic.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface FeatureNavigation {
    fun register(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController
    )
}