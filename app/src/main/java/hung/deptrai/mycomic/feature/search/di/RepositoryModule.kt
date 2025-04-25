package hung.deptrai.mycomic.feature.search.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hung.deptrai.mycomic.feature.search.data.repository.SearchAuthorRepositoryImpl
import hung.deptrai.mycomic.feature.search.domain.repository.SearchComicRepository
import hung.deptrai.mycomic.feature.search.data.repository.SearchComicRepositoryImpl
import hung.deptrai.mycomic.feature.search.data.repository.SearchScanlationGroupRepositoryImpl
import hung.deptrai.mycomic.feature.search.domain.repository.SearchAuthorRepository
import hung.deptrai.mycomic.feature.search.domain.repository.SearchScanlationGroupRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSearchComicRepository(
        impl: SearchComicRepositoryImpl
    ): SearchComicRepository

    @Binds
    @Singleton
    abstract fun bindSearchAuthorRepository(
        impl: SearchAuthorRepositoryImpl
    ): SearchAuthorRepository

    @Binds
    @Singleton
    abstract fun bindSearchScanlationGroupRepository(
        impl: SearchScanlationGroupRepositoryImpl
    ): SearchScanlationGroupRepository
}