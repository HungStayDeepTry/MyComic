package hung.deptrai.mycomic.feature.search.domain.usecase

import hung.deptrai.mycomic.feature.search.domain.repository.SearchAuthorRepository
import hung.deptrai.mycomic.feature.search.domain.repository.SearchRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

}