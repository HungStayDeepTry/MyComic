package hung.deptrai.mycomic.core.di
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hung.deptrai.constants.MdConstants
import hung.deptrai.mycomic.core.network.TokenApi
import hung.deptrai.mycomic.core.network.author.AuthorAPI
import hung.deptrai.mycomic.core.network.coverArt.CoverArtAPI
import hung.deptrai.mycomic.core.network.manga.ChapterAPI
import hung.deptrai.mycomic.core.network.scanlationGroup.ScanlationGroupAPI
import hung.deptrai.mycomic.core.network.statistic.SearchStatisticsAPI
import hung.deptrai.mycomic.core.network.user.UserSearchAPI
import hung.deptrai.mycomic.core.network.manga.MangaAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(MdConstants.Api.baseUrl)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    fun provideSearchComicAPI(retrofit: Retrofit): MangaAPI {
        return retrofit.create(MangaAPI::class.java)
    }

    @Provides
    fun provideSearchChapterAPI(retrofit: Retrofit): ChapterAPI {
        return retrofit.create(ChapterAPI::class.java)
    }

    @Provides
    fun provideSearchCovertArtAPI(retrofit: Retrofit): CoverArtAPI{
        return retrofit.create(CoverArtAPI::class.java)
    }

    @Provides
    fun provideSearchAuthorByIdAPI(retrofit: Retrofit): AuthorAPI{
        return retrofit.create(AuthorAPI::class.java)
    }

    @Provides
    fun provideStatisticsSearchAPI(retrofit: Retrofit): SearchStatisticsAPI{
        return retrofit.create(SearchStatisticsAPI::class.java)
    }

    @Provides
    fun provideSearchScanlationGroupByTitleAPI(retrofit: Retrofit): ScanlationGroupAPI{
        return retrofit.create(ScanlationGroupAPI::class.java)
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