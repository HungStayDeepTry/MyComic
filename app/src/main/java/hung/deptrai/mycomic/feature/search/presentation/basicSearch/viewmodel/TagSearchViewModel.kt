package hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.feature.search.domain.usecase.SearchTagUseCase
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.Result
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.TagSearch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagSearchViewModel @Inject constructor(
    private val searchTagUseCase: SearchTagUseCase
) : ViewModel(){
    private val _tagState = MutableStateFlow<Result<List<TagSearch>>>(Result.Loading)
    val tagState: StateFlow<Result<List<TagSearch>>> = _tagState

    fun getTags(){
        viewModelScope.launch {
            _tagState.value = Result.Loading
            try {
                val result = searchTagUseCase.takeAllTags()
                val data = (result as? ResultWrapper.Success<*>)?.data
                val rs = (data as List<TagSearch>)
                if (rs.isNotEmpty()) {
                    _tagState.value = Result.Success(rs)
                } else {
                    _tagState.value = Result.Error(Exception("Không tìm thấy kết quả"))
                }
            } catch (e: Exception) {
                _tagState.value = Result.Error(e)
            }
        }
    }
}