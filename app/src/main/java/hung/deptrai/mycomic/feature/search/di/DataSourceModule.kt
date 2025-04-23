package hung.deptrai.mycomic.feature.search.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchDataSource
import hung.deptrai.mycomic.feature.search.data.remote.datasource.SearchDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindSearchDataSource(
        impl: SearchDataSourceImpl
    ): SearchDataSource
}