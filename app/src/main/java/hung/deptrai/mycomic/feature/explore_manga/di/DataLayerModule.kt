package hung.deptrai.mycomic.feature.explore_manga.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hung.deptrai.mycomic.feature.explore_manga.data.local.HomeLocalDataSource
import hung.deptrai.mycomic.feature.explore_manga.data.local.HomeLocalDataSourceImpl
import hung.deptrai.mycomic.feature.explore_manga.data.remote.datasource.MangaPageDataSource
import hung.deptrai.mycomic.feature.explore_manga.data.remote.datasource.MangaPageDataSourceImpl
import hung.deptrai.mycomic.feature.explore_manga.data.remote.repository.MangaPageRepositoryImpl
import hung.deptrai.mycomic.feature.explore_manga.domain.MangaPageRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataLayerModule {
    @Binds
    @Singleton
    abstract fun bindHomeLocalDataSource(
        impl: HomeLocalDataSourceImpl
    ): HomeLocalDataSource

    @Binds
    @Singleton
    abstract fun bindMangaPageRepository(
        impl: MangaPageRepositoryImpl
    ): MangaPageRepository

    @Binds
    @Singleton
    abstract fun bindMangaPageDataSource(
        impl: MangaPageDataSourceImpl
    ): MangaPageDataSource
}