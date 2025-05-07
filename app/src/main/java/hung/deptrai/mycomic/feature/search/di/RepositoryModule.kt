package hung.deptrai.mycomic.feature.search.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hung.deptrai.mycomic.feature.search.data.repository.SearchRepositoryImpl
import hung.deptrai.mycomic.feature.search.data.repository.TokenRepositoryImpl
import hung.deptrai.mycomic.feature.search.domain.repository.SearchRepository
import hung.deptrai.mycomic.feature.search.domain.repository.TokenRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        impl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    @Singleton
    abstract fun bindTokenRepository(
        impl: TokenRepositoryImpl
    ): TokenRepository

}