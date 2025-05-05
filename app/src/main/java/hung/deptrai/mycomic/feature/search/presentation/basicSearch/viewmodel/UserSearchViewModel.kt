package hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.feature.search.domain.usecase.SearchUserUseCase
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.Result
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.UserSearch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSearchViewModel @Inject constructor(
    private val userSearchUserUseCase: SearchUserUseCase
) : ViewModel(){
    private val _searchState = MutableStateFlow<Result<List<UserSearch>>>(Result.Loading)
    val searchState: StateFlow<Result<List<UserSearch>>> = _searchState

    fun getUsers(token: String, ids: List<String>){
        viewModelScope.launch {
            _searchState.value = Result.Loading
            try {
                val result = userSearchUserUseCase.getUsersByGroup(token, ids)
                val data = (result as? ResultWrapper.Success<*>)?.data
                val rs = (data as List<UserSearch>)
                if (rs.isNotEmpty()) {
                    _searchState.value = Result.Success(rs)
                } else {
                    _searchState.value = Result.Error(Exception("Không tìm thấy kết quả"))
                }
            } catch (e: Exception) {
                _searchState.value = Result.Error(e)
            }
        }
    }
}