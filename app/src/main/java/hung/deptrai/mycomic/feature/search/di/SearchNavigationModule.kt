package hung.deptrai.mycomic.feature.search.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import hung.deptrai.mycomic.core.navigation.FeatureNavigation
import hung.deptrai.mycomic.feature.mock_login.navigation.LoginNavigationImpl
import hung.deptrai.mycomic.feature.search.navigation.SearchNavigationImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchNavigationModule {
    @Binds
    @IntoSet
    abstract fun bindSearchNavigation(
        impl: SearchNavigationImpl
    ): FeatureNavigation
}