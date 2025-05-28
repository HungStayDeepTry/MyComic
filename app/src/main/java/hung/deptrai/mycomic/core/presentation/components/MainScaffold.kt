package hung.deptrai.mycomic.core.presentation.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import hung.deptrai.mycomic.core.navigation.FeatureNavigation
import hung.deptrai.mycomic.navigation.AppNavHost
import kotlin.math.min

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    featureNavigation: Set<@JvmSuppressWildcards FeatureNavigation>
) {
    val scrollState = rememberScrollState()
    var alpha by remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent) // hoặc màu nền bạn muốn
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            // Content có thể cuộn
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            // Cho phép cuộn dọc
            ) {
                // Header cố định trên cùng
                HeaderRow(
                    {

                    },
                    {

                    },
                    {

                    },
                    {

                    },
                    modifier = Modifier
                        .zIndex(2f)
                        .background(MaterialTheme.colorScheme.background.copy(alpha = alpha))
                )
                AppNavHost(
                    featureNavigation = featureNavigation,
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(1f),
                    onScrollStateChanged = {
                        it2 ->
                        alpha = min(1f, it2.value.toFloat() / 100)
                    }
                )
            }
        }
    }
}
