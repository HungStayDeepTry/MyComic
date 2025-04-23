package hung.deptrai.mycomic.feature.search.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hung.deptrai.mycomic.feature.search.domain.repository.SearchComicRepository
import hung.deptrai.mycomic.feature.search.data.repository.SearchComicRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSearchComicRepository(
        impl: SearchComicRepositoryImpl
    ): SearchComicRepository
}