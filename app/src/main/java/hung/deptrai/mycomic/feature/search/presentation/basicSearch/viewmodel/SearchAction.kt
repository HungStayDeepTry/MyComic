package hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel

import hung.deptrai.mycomic.feature.search.domain.SearchType

sealed class SearchAction {
    data class TabRowClick(val type: SearchType): SearchAction()
    data class TextChange(val title: String): SearchAction()
}