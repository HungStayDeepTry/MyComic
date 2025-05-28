package hung.deptrai.mycomic.navigation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import hung.deptrai.mycomic.core.navigation.FeatureNavigation

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    featureNavigation: Set<@JvmSuppressWildcards FeatureNavigation>,
    modifier: Modifier,
    onScrollStateChanged: (ScrollState) -> Unit
) {
    NavHost(
        navController, startDestination = "home",
        modifier
    ) {
        featureNavigation.forEach {
            it.register(this, navController, onScrollStateChanged = { it1 ->
                onScrollStateChanged(it1)
            })
        }
    }
}