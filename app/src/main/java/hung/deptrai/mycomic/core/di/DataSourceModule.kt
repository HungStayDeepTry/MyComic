package hung.deptrai.mycomic.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hung.deptrai.mycomic.core.data.remote.datasource.StatisticDataSource
import hung.deptrai.mycomic.core.data.remote.datasource.StatisticDataSourceImpl
import hung.deptrai.mycomic.feature.search.data.remote.datasource.impl.SearchComicDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindSearchDataSource(
        impl: StatisticDataSourceImpl
    ): StatisticDataSource
}