package hung.deptrai.mycomic.core.presentation.components

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import hung.deptrai.mycomic.core.navigation.FeatureNavigation
import hung.deptrai.mycomic.navigation.AppNavHost

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    featureNavigation: Set<@JvmSuppressWildcards FeatureNavigation>
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HeaderRow(
                {

                },
                {

                },
                {

                }
            ) { }
        }
    ) {
        AppNavHost(
            featureNavigation = featureNavigation
        )
    }
}