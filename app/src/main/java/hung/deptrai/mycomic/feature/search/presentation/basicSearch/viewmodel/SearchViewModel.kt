package hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.feature.search.domain.SearchType
import hung.deptrai.mycomic.feature.search.domain.model.AuthorSearch
import hung.deptrai.mycomic.feature.search.domain.model.ScanlationGroupSearch
import hung.deptrai.mycomic.feature.search.domain.model.SearchComic
import hung.deptrai.mycomic.feature.search.domain.usecase.SearchUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    init {
        // Lắng nghe inputText thay đổi và debounce 500ms
        viewModelScope.launch {
            combine(
                uiState.map { it.inputText },
                uiState.map { it.typeInput }
            ) { inputText, typeInput ->
                inputText to typeInput
            }
                .debounce(500)
                .distinctUntilChanged()
                .collectLatest { (query, type) ->
                    if (query.isNotBlank()) {
                        search(query, type)
                    }
                }
        }
    }

    private fun setTypeInput(type: SearchType) {
        _uiState.update { it.copy(typeInput = type) }
    }

    private fun textChanged(title: String) {
        _uiState.update { it.copy(inputText = title, hasReload = true) }
    }

    fun onEvent(action: SearchAction) {
        when (action) {
            is SearchAction.TextChange -> textChanged(action.title)
            is SearchAction.TabRowClick -> setTypeInput(action.type)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun search(title: String, type: SearchType) {
        val text = _uiState.value.inputText.trim()
        viewModelScope.launch {
            if (text.isEmpty()) {
                _uiState.update { it.copy(event = SearchEvent.Error(PresentationError.EmptyInput.asUiText())) }
            } else {
                if (!_uiState.value.hasReload) {
                    if (_uiState.value.hasEmpty) {
                        _uiState.update { it.copy(event = SearchEvent.Error(PresentationError.EmptyList.asUiText())) }
                    } else {
                        _uiState.update { it.copy(event = SearchEvent.Success) }
                    }
                } else {
                    _uiState.update { it.copy(isLoading = true, event = SearchEvent.Loading) }

                    val result = searchUseCase.search(title, type)

                    if (type == SearchType.ALL) {
                        val comicsTemp = mutableListOf<SearchComic>()
                        val authorsTemp = mutableListOf<AuthorSearch>()
                        val groupsTemp = mutableListOf<ScanlationGroupSearch>()

                        var hasError = 0
                        var errorMessage: UiText? = null

                        result.forEach {
                            when (it) {
                                is Result.Success -> {
                                    comicsTemp += it.data.filterIsInstance<SearchComic>()
                                    authorsTemp += it.data.filterIsInstance<AuthorSearch>()
                                    groupsTemp += it.data.filterIsInstance<ScanlationGroupSearch>()
                                }

                                is Result.Error -> {
                                    hasError += 1
                                    errorMessage = it.error.asUiText()
                                }
                            }
                        }

                        val hasEmpty = comicsTemp.isEmpty() && authorsTemp.isEmpty() && groupsTemp.isEmpty()
                        val event = when {
                            hasEmpty -> SearchEvent.Error(PresentationError.EmptyList.asUiText())
                            hasError == 3 -> SearchEvent.Error(errorMessage!!)
                            else -> SearchEvent.Success
                        }

                        _uiState.update {
                            it.copy(
                                comics = comicsTemp,
                                authors = authorsTemp,
                                groups = groupsTemp,
                                hasEmpty = hasEmpty,
                                hasReload = false,
                                isLoading = false,
                                event = event,
                                errorEvent = if (hasEmpty || hasError == 3) SearchEvent.Error(errorMessage!!) else null
                            )
                        }
                    } else {
                        result.forEach {
                            when (it) {
                                is Result.Success -> {
                                    val data = it.data
                                    val comicList = data.filterIsInstance<SearchComic>()
                                    val authorList = data.filterIsInstance<AuthorSearch>()
                                    val groupList = data.filterIsInstance<ScanlationGroupSearch>()

                                    when (type) {
                                        SearchType.COMIC -> updateListAndEvent(comicList, type)
                                        SearchType.AUTHOR -> updateListAndEvent(authorList, type)
                                        SearchType.GROUP -> updateListAndEvent(groupList, type)
                                        else -> Unit
                                    }
                                }

                                is Result.Error -> {
                                    val message = it.error.asUiText()
                                    val errorEvent = when (type) {
                                        SearchType.COMIC -> SearchEvent.ErrorComic(message)
                                        SearchType.AUTHOR -> SearchEvent.ErrorAuthor(message)
                                        SearchType.GROUP -> SearchEvent.ErrorGroup(message)
                                        else -> SearchEvent.Error(message)
                                    }
                                    _uiState.update { state ->
                                        state.copy(
                                            isLoading = false,
                                            event = errorEvent,
                                            errorEvent = errorEvent
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun <T> updateListAndEvent(list: List<T>, type: SearchType) {
        val event = if (list.isEmpty()) SearchEvent.Error(PresentationError.EmptyList.asUiText()) else SearchEvent.Success
        _uiState.update { state ->
            when (type) {
                SearchType.COMIC -> state.copy(comics = list.filterIsInstance<SearchComic>(), isLoading = false, event = event)
                SearchType.AUTHOR -> state.copy(authors = list.filterIsInstance<AuthorSearch>(), isLoading = false, event = event)
                SearchType.GROUP -> state.copy(groups = list.filterIsInstance<ScanlationGroupSearch>(), isLoading = false, event = event)
                else -> state
            }
        }
    }
}

data class SearchUiState(
    val comics: List<SearchComic> = emptyList(),
    val authors: List<AuthorSearch> = emptyList(),
    val groups: List<ScanlationGroupSearch> = emptyList(),
    val inputText: String = "",
    val typeInput: SearchType = SearchType.ALL,
    val isLoading: Boolean = false,
    val hasReload: Boolean = true,
    val hasEmpty: Boolean = false,
    val event: SearchEvent? = null,
    val errorEvent: SearchEvent? = null
)

