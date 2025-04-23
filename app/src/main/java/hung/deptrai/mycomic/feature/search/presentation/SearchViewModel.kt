package hung.deptrai.mycomic.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hung.deptrai.mycomic.feature.search.domain.usecase.SearchComicUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchComicUseCase: SearchComicUseCase
) :ViewModel(){
    private val _searchState = MutableStateFlow<Result<List<SearchComic>>>(Result.Loading)
    val searchState: StateFlow<Result<List<SearchComic>>> = _searchState

    fun searchComic(title: String) {
        viewModelScope.launch {
            _searchState.value = Result.Loading
            try {
                val result = searchComicUseCase.searchComicByTitle(title)
                if (result.isNotEmpty()) {
                    _searchState.value = Result.Success(result)
                } else {
                    _searchState.value = Result.Error(Exception("No data found"))
                }
            } catch (e: Exception) {
                _searchState.value = Result.Error(e)
            }
        }
    }
}