package hung.deptrai.mycomic.feature.search.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.feature.search.domain.usecase.SearchAuthorUseCase
import hung.deptrai.mycomic.feature.search.presentation.AuthorSearch
import javax.inject.Inject

@HiltViewModel
class AuthorSearchViewModel @Inject constructor(
    private val searchAuthorUseCase: SearchAuthorUseCase
) : SearchViewModel<AuthorSearch>() {

    override suspend fun searchData(title: String): List<AuthorSearch> {
        val result = searchAuthorUseCase.searchAuthorByTitle(title)
        val data = (result as? ResultWrapper.Success<*>)?.data
        return data as? List<AuthorSearch> ?: emptyList()
    }
}