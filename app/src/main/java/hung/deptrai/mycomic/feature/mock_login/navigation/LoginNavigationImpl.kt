package hung.deptrai.mycomic.feature.mock_login.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import hung.deptrai.mycomic.core.navigation.FeatureNavigation
import hung.deptrai.mycomic.feature.mock_login.LoginScreen
import javax.inject.Inject

class LoginNavigationImpl @Inject constructor() : FeatureNavigation {
    override fun register(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.composable("login"){
            LoginScreen(LocalContext.current)
        }
    }
}