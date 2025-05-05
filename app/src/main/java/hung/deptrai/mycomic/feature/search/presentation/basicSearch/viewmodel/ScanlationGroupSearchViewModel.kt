package hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import hung.deptrai.mycomic.core.common.ResultWrapper
import hung.deptrai.mycomic.feature.search.domain.usecase.SearchScanlationGroupUseCase
import hung.deptrai.mycomic.feature.search.domain.usecase.SearchUserUseCase
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.ScanlationGroupSearch
import javax.inject.Inject

@HiltViewModel
class ScanlationGroupSearchViewModel @Inject constructor(
    private val searchScanlationGroupUseCase: SearchScanlationGroupUseCase,
    private val searchUserUseCase: SearchUserUseCase
) : SearchViewModel<ScanlationGroupSearch>() {

    override suspend fun searchData(title: String): List<ScanlationGroupSearch> {
        val result = searchScanlationGroupUseCase.searchScanlationGroupByTitle(title)
        val data = (result as? ResultWrapper.Success<*>)?.data
        return data as? List<ScanlationGroupSearch> ?: emptyList()
    }
}