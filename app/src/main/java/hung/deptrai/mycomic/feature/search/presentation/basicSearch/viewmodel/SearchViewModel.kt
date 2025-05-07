package hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hung.deptrai.mycomic.R
import hung.deptrai.mycomic.core.domain.wrapper.Result
import hung.deptrai.mycomic.feature.search.domain.SearchType
import hung.deptrai.mycomic.feature.search.domain.model.AuthorSearch
import hung.deptrai.mycomic.feature.search.domain.model.ScanlationGroupSearch
import hung.deptrai.mycomic.feature.search.domain.model.SearchComic
import hung.deptrai.mycomic.feature.search.domain.usecase.SearchUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    val inputText = MutableStateFlow("")

    fun textChanged(title: String){
        inputText.value = title
    }

    fun search(title: String, type: SearchType) {
        val text = inputText.value.trim()
        viewModelScope.launch {
            if (text.isEmpty()) {
                eventChannel.emit(SearchEvent.Error(PresentationError.EmptyInput.asUiText()))
            } else {
                eventChannel.emit(SearchEvent.Loading) // ✅ Gửi trạng thái Loading

                when (val result = searchUseCase.search(title, type)) {
                    is Result.Success -> {
                        val data = result.data

                        val comicList = data.filterIsInstance<SearchComic>()
                        val authorList = data.filterIsInstance<AuthorSearch>()
                        val groupList = data.filterIsInstance<ScanlationGroupSearch>()

                        when (type) {
                            SearchType.COMIC -> {
                                _comics.value = comicList
                                if (comicList.isEmpty()) {
                                    eventChannel.emit(SearchEvent.Empty)
                                } else {
                                    eventChannel.emit(SearchEvent.Success)
                                }
                            }

                            SearchType.AUTHOR -> {
                                _authors.value = authorList
                                if (comicList.isEmpty()) {
                                    eventChannel.emit(SearchEvent.Empty)
                                } else {
                                    eventChannel.emit(SearchEvent.Success)
                                }
                            }

                            SearchType.SCANLATION_GROUP -> {
                                _groups.value = groupList
                                if (comicList.isEmpty()) {
                                    eventChannel.emit(SearchEvent.Empty)
                                } else {
                                    eventChannel.emit(SearchEvent.Success)
                                }
                            }

                            SearchType.ALL -> {
                                _comics.value = comicList
                                _authors.value = authorList
                                _groups.value = groupList

                                if (comicList.isEmpty() && authorList.isEmpty() && groupList.isEmpty()) {
                                    eventChannel.emit(SearchEvent.Empty)
                                } else {
                                    eventChannel.emit(SearchEvent.Success)
                                }
                            }
                        }
                    }

                    is Result.Error -> {
                        eventChannel.emit(SearchEvent.Error(result.error.asUiText()))
                    }
                }
            }
        }
    }
}
