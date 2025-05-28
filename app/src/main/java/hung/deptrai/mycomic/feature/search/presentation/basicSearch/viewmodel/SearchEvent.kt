package hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel

import hung.deptrai.mycomic.core.presentation.BaseEvent

sealed interface SearchEvent : BaseEvent{
    data class Error(val message: UiText) : SearchEvent
    data object Loading : SearchEvent
    data object Success: SearchEvent

    data class ErrorComic(val message: UiText) : SearchEvent
    data class ErrorAuthor(val message: UiText) : SearchEvent
    data class ErrorGroup(val message: UiText) : SearchEvent
}