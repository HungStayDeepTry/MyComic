package hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel

sealed interface SearchEvent {
    data class Error(val message: UiText) : SearchEvent
    object Empty : SearchEvent
    object Loading : SearchEvent
    object Success: SearchEvent

    data class ErrorComic(val message: UiText) : SearchEvent
    data class ErrorAuthor(val message: UiText) : SearchEvent
    data class ErrorGroup(val message: UiText) : SearchEvent
}