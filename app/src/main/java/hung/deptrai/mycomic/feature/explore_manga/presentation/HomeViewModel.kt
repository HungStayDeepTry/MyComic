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

    // need refactor
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _mangas = MutableStateFlow<List<MangaHome>>(emptyList())
    val mangas: StateFlow<List<MangaHome>> = _mangas

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadManga(isRefresh: Boolean = false) {
        viewModelScope.launch {
            _isRefreshing.value = true
            mangapageRepo.fetchMangaPageInfo(isRefresh)
                .catch { e -> /* handle error */ }
                .collect { result ->
                    _mangas.value = result
                    _isRefreshing.value = false
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