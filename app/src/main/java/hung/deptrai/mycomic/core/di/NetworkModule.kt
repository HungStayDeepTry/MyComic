package hung.deptrai.mycomic.core.di
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hung.deptrai.mycomic.core.network.TokenApi
import hung.deptrai.mycomic.core.network.author.SearchAuthorAPI
import hung.deptrai.mycomic.core.network.coverArt.CoverArtAPI
import hung.deptrai.mycomic.core.network.scanlationGroup.SearchScanlationGroupAPI
import hung.deptrai.mycomic.core.network.statistic.SearchStatisticsAPI
import hung.deptrai.mycomic.core.network.user.UserSearchAPI
import hung.deptrai.mycomic.core.network.MangaAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.mangadex.org/")
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    fun provideSearchComicAPI(retrofit: Retrofit): MangaAPI {
        return retrofit.create(MangaAPI::class.java)
    }

    @Provides
    fun provideSearchCovertArtAPI(retrofit: Retrofit): CoverArtAPI{
        return retrofit.create(CoverArtAPI::class.java)
    }

    @Provides
    fun provideSearchAuthorByIdAPI(retrofit: Retrofit): SearchAuthorAPI{
        return retrofit.create(SearchAuthorAPI::class.java)
    }

    @Provides
    fun provideStatisticsSearchAPI(retrofit: Retrofit): SearchStatisticsAPI{
        return retrofit.create(SearchStatisticsAPI::class.java)
    }

    @Provides
    fun provideSearchScanlationGroupByTitleAPI(retrofit: Retrofit): SearchScanlationGroupAPI{
        return retrofit.create(SearchScanlationGroupAPI::class.java)
    }

    @Provides
    fun provideSearchUserByIdAPI(retrofit: Retrofit): UserSearchAPI{
        return retrofit.create(UserSearchAPI::class.java)
    }

    @Provides
    fun provideTokenAPI(retrofit: Retrofit): TokenApi{
        return retrofit.create(TokenApi::class.java)
    }
}