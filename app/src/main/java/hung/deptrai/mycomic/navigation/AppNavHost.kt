package hung.deptrai.mycomic.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import hung.deptrai.mycomic.core.navigation.FeatureNavigation

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    featureNavigation: Set<@JvmSuppressWildcards FeatureNavigation>
) {
    NavHost(navController, startDestination = "home") {
        featureNavigation.forEach {
            it.register(this, navController)
        }
    }
}