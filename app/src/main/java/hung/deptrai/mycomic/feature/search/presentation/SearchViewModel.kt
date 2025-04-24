package hung.deptrai.mycomic.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hung.deptrai.mycomic.core.common.ResultWrapper
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
                val manga = (result as? ResultWrapper.Success<*>)?.data
                val rs2 = manga as? List<SearchComic>

                if (rs2?.isNotEmpty() == true) {
                    _searchState.value = Result.Success(manga)
                } else {
                    _searchState.value = Result.Error(Exception("Không tìm thấy truyện nào"))
                }

            } catch (e: Exception) {
                _searchState.value = Result.Error(e)
            }
        }
    }
}