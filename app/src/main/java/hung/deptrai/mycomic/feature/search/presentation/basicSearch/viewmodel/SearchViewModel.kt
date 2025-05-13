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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val _comics = MutableStateFlow<List<SearchComic>>(emptyList())
    val comics: StateFlow<List<SearchComic>> = _comics

    private val _authors = MutableStateFlow<List<AuthorSearch>>(emptyList())
    val authors: StateFlow<List<AuthorSearch>> = _authors

    private val _groups = MutableStateFlow<List<ScanlationGroupSearch>>(emptyList())
    val groups: StateFlow<List<ScanlationGroupSearch>> = _groups

    private val eventChannel = MutableSharedFlow<SearchEvent>()
    val events = eventChannel.asSharedFlow()


    private val _errorEvent = MutableSharedFlow<SearchEvent>()
    val errorEvent = _errorEvent.asSharedFlow()

    private val _hasReload = MutableStateFlow(true)
    private val _hasEmpty = MutableStateFlow(false)

    val inputText = MutableStateFlow("")
    private val _typeInput = mutableStateOf(SearchType.ALL)

    init {
        // Lắng nghe inputText thay đổi và debounce 500ms
        viewModelScope.launch {
            inputText
                .debounce(500)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isNotBlank()) {
                        search(query, _typeInput.value)
                    }
                }
        }
    }

    fun setTypeInput(type: SearchType) {
        _typeInput.value = type
    }

    fun textChanged(title: String) {
        inputText.value = title
        _hasReload.value = true
    }

    @SuppressLint("SuspiciousIndentation")
    fun search(title: String, type: SearchType) {
        val text = inputText.value.trim()
        viewModelScope.launch {
            if (text.isEmpty()) {
                eventChannel.emit(SearchEvent.Error(PresentationError.EmptyInput.asUiText()))
            } else {
                if(!_hasReload.value){
                    if(_hasEmpty.value){
                        eventChannel.emit(SearchEvent.Error(PresentationError.EmptyList.asUiText()))
                    } else{
                        eventChannel.emit(SearchEvent.Success)
                    }
                } else {
                    eventChannel.emit(SearchEvent.Loading)

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

                        _comics.value = comicsTemp
                        _authors.value = authorsTemp
                        _groups.value = groupsTemp

                        if (comicsTemp.isEmpty() && authorsTemp.isEmpty() && groupsTemp.isEmpty()) {
                            eventChannel.emit(SearchEvent.Error(PresentationError.EmptyList.asUiText()))
                            _errorEvent.emit(SearchEvent.Error(errorMessage!!))
                            _hasEmpty.value = true
                        } else if (hasError == 3) {
                            eventChannel.emit(SearchEvent.Error(errorMessage!!))
                            _hasEmpty.value = true
                        } else {
                            _hasEmpty.value = false
                            eventChannel.emit(SearchEvent.Success)
                        }
                        _hasReload.value = false
                    } else {
                        // Không phải SearchType.ALL, giữ nguyên xử lý hiện tại
                        result.forEach {
                            when (it) {
                                is Result.Success -> {
                                    val data = it.data

                                    val comicList = data.filterIsInstance<SearchComic>()
                                    val authorList = data.filterIsInstance<AuthorSearch>()
                                    val groupList = data.filterIsInstance<ScanlationGroupSearch>()

                                    when (type) {
                                        SearchType.COMIC -> {
                                            _comics.value = comicList
                                            emitResultOrEmpty(comicList)
                                        }

                                        SearchType.AUTHOR -> {
                                            _authors.value = authorList
                                            emitResultOrEmpty(authorList)
                                        }

                                        SearchType.GROUP -> {
                                            _groups.value = groupList
                                            emitResultOrEmpty(groupList)
                                        }

                                        else -> Unit
                                    }
                                }

                                is Result.Error -> {
                                    val message = it.error.asUiText()
                                    val errorEvent = when (type) {
                                        SearchType.COMIC -> SearchEvent.ErrorComic(message)
                                        SearchType.AUTHOR -> SearchEvent.ErrorAuthor(message)
                                        SearchType.GROUP -> SearchEvent.ErrorGroup(
                                            message
                                        )

                                        else -> SearchEvent.Error(message)
                                    }
                                    eventChannel.emit(errorEvent)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun <T> emitResultOrEmpty(list: List<T>) {
        if (list.isEmpty()) {
            eventChannel.emit(SearchEvent.Error(PresentationError.EmptyList.asUiText()))
        } else {
            eventChannel.emit(SearchEvent.Success)
        }
    }
}
