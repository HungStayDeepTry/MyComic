package hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel

sealed interface SearchEvent {
    data class Error(val message: UiText) : SearchEvent
    object Empty : SearchEvent
    object Loading : SearchEvent
    object Success: SearchEvent
}