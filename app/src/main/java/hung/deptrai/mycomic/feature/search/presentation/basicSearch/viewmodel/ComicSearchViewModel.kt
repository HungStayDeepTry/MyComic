package hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.feature.search.domain.usecase.SearchComicUseCase
import hung.deptrai.mycomic.feature.search.domain.model.SearchComic
import javax.inject.Inject

@HiltViewModel
class ComicSearchViewModel @Inject constructor(
    private val searchComicUseCase: SearchComicUseCase
) : SearchViewModel<SearchComic>() {

    override suspend fun searchData(title: String): List<SearchComic> {
        val result = searchComicUseCase.searchComicByTitle(title)
        val data = (result as? ResultWrapper.Success<*>)?.data
        return data as? List<SearchComic> ?: emptyList()
    }
}