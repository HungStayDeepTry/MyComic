package hung.deptrai.mycomic.feature.explore_manga.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hung.deptrai.mycomic.feature.explore_manga.domain.MangaHome
import hung.deptrai.mycomic.feature.explore_manga.domain.MangaPageRepository
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel.SearchAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mangapageRepo: MangaPageRepository
) :ViewModel(){

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadManga(isRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)
            mangapageRepo.fetchMangaPageInfo(isRefresh)
                .catch { e ->
                    // handle error if needed
                    _uiState.value = _uiState.value.copy(isRefreshing = false)
                }
                .collect { result ->
                    _uiState.value = _uiState.value.copy(
                        mangas = result,
                        isRefreshing = false
                    )
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(action: HomePageAction) {
        when (action) {
            is HomePageAction.PullToRefresh -> loadManga(action.isRefresh)
        }
    }
}
data class HomeUiState(
    val isRefreshing: Boolean = false,
    val mangas: List<MangaHome> = emptyList()
)