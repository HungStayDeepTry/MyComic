package hung.deptrai.mycomic.feature.search.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hung.deptrai.mycomic.feature.search.domain.model.AuthorSearch
import hung.deptrai.mycomic.feature.search.domain.model.ScanlationGroupSearch
import hung.deptrai.mycomic.feature.search.domain.model.SearchComic
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel.AuthorSearchViewModel
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel.ComicSearchViewModel
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel.ScanlationGroupSearchViewModel
import hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel.SearchViewModel
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
