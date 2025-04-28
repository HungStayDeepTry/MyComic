package hung.deptrai.mycomic.feature.search.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchAuthorDataSource
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchComicDataSource
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchScanlationGroupDataSource
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchUserDataSource
import hung.deptrai.mycomic.feature.search.data.remote.datasource.TokenDatasource
import hung.deptrai.mycomic.feature.search.data.remote.datasource.impl.SearchAuthorDataSourceImpl
import hung.deptrai.mycomic.feature.search.data.remote.datasource.impl.SearchComicDataSourceImpl
import hung.deptrai.mycomic.feature.search.data.remote.datasource.impl.SearchScanlationGroupDataSourceImpl
import hung.deptrai.mycomic.feature.search.data.remote.datasource.impl.SearchUserDataSourceImpl
import hung.deptrai.mycomic.feature.search.data.remote.datasource.impl.TokenDatasourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindSearchDataSource(
        impl: SearchComicDataSourceImpl
    ): SearchComicDataSource

    @Binds
    @Singleton
    abstract fun bindSearchAuthorDataSource(
        impl: SearchAuthorDataSourceImpl
    ) : SearchAuthorDataSource

    @Binds
    @Singleton
    abstract fun bindSearchScanlationDataSource(
        impl: SearchScanlationGroupDataSourceImpl
    ) : SearchScanlationGroupDataSource

    @Binds
    @Singleton
    abstract fun bindSearchUserDataSource(
        impl: SearchUserDataSourceImpl
    ) : SearchUserDataSource

    @Binds
    @Singleton
    abstract fun bindTokenDataSource(
        impl: TokenDatasourceImpl
    ) : TokenDatasource
}