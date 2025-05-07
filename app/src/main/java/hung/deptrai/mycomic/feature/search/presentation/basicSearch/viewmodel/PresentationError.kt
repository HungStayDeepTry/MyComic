package hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel

sealed class PresentationError {
    object EmptyInput : PresentationError()
}