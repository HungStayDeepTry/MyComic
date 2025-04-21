package hung.deptrai.mycomic.feature.mock_login.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import hung.deptrai.mycomic.core.navigation.FeatureNavigation
import hung.deptrai.mycomic.feature.mock_login.navigation.LoginNavigationImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginNavigationModule {
    @Binds
    @IntoSet
    abstract fun bindSearchNavigation(
        impl: LoginNavigationImpl
    ): FeatureNavigation
}