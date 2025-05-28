package hung.deptrai.mycomic.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hung.deptrai.mycomic.core.data.remote.datasource.AuthorDataSource
import hung.deptrai.mycomic.core.data.remote.datasource.AuthorDataSourceImpl
import hung.deptrai.mycomic.core.data.remote.datasource.ChapterDataSource
import hung.deptrai.mycomic.core.data.remote.datasource.ChapterDataSourceImpl
import hung.deptrai.mycomic.core.data.remote.datasource.MangaDataSource
import hung.deptrai.mycomic.core.data.remote.datasource.MangaDataSourceImpl
import hung.deptrai.mycomic.core.data.remote.datasource.ScanlationGroupDataSource
import hung.deptrai.mycomic.core.data.remote.datasource.ScanlationGroupDataSourceImpl
import hung.deptrai.mycomic.core.data.remote.datasource.StatisticDataSource
import hung.deptrai.mycomic.core.data.remote.datasource.StatisticDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindStatisticDataSource(
        impl: StatisticDataSourceImpl
    ): StatisticDataSource

    @Binds
    @Singleton
    abstract fun bindScanGroupDataSource(
        impl: ScanlationGroupDataSourceImpl
    ): ScanlationGroupDataSource

    @Binds
    @Singleton
    abstract fun bindChapterDataSource(
        impl: ChapterDataSourceImpl
    ): ChapterDataSource

    @Binds
    @Singleton
    abstract fun bindAuthorDataSource(
        impl: AuthorDataSourceImpl
    ): AuthorDataSource

    @Binds
    @Singleton
    abstract fun bindMangaDataSource(
        impl: MangaDataSourceImpl
    ): MangaDataSource
}