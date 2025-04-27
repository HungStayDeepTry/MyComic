package hung.deptrai.mycomic.feature.search.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hung.deptrai.mycomic.feature.search.data.repository.SearchAuthorRepositoryImpl
import hung.deptrai.mycomic.feature.search.data.repository.SearchComicRepositoryImpl
import hung.deptrai.mycomic.feature.search.data.repository.SearchScanlationGroupRepositoryImpl
import hung.deptrai.mycomic.feature.search.domain.repository.SearchAuthorRepository
import hung.deptrai.mycomic.feature.search.domain.repository.SearchComicRepository
import hung.deptrai.mycomic.feature.search.domain.repository.SearchScanlationGroupRepository
import hung.deptrai.mycomic.feature.search.presentation.AuthorSearch
import hung.deptrai.mycomic.feature.search.presentation.ScanlationGroupSearch
import hung.deptrai.mycomic.feature.search.presentation.SearchComic
import hung.deptrai.mycomic.feature.search.presentation.viewmodel.AuthorSearchViewModel
import hung.deptrai.mycomic.feature.search.presentation.viewmodel.ComicSearchViewModel
import hung.deptrai.mycomic.feature.search.presentation.viewmodel.ScanlationGroupSearchViewModel
import hung.deptrai.mycomic.feature.search.presentation.viewmodel.SearchViewModel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ViewModelModule {

    @Binds
    @Singleton
    abstract fun bindSearchComicViewModel(
        impl: ComicSearchViewModel
    ): SearchViewModel<SearchComic>

    @Binds
    @Singleton
    abstract fun bindSearchAuthorViewModel(
        impl: AuthorSearchViewModel
    ): SearchViewModel<AuthorSearch>

    @Binds
    @Singleton
    abstract fun bindSearchScanlationGroupViewModel(
        impl: ScanlationGroupSearchViewModel
    ): SearchViewModel<ScanlationGroupSearch>
}
