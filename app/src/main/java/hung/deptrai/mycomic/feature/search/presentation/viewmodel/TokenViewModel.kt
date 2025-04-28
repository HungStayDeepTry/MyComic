package hung.deptrai.mycomic.feature.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hung.deptrai.mycomic.feature.search.domain.usecase.TokenManageUserCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val tokenManageUserCase: TokenManageUserCase
) : ViewModel(){
    private val _tokenState = MutableStateFlow<String>("")
    val tokenState: StateFlow<String> = _tokenState

    fun saveToken(token: String) {
        viewModelScope.launch {
            tokenManageUserCase.saveToken(token)
        }
    }

    fun readToken() {
        viewModelScope.launch {
            tokenManageUserCase.readToken().collect { token ->
                _tokenState.value = token
            }
        }
    }
}