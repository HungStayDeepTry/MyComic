package hung.deptrai.mycomic.feature.explore_manga.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hung.deptrai.mycomic.core.data.local.database.AppDatabase
import hung.deptrai.mycomic.feature.explore_manga.data.local.dao.HomeMangaDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "mycomic_db"
        ).build()
    }

    @Provides
    fun provideHomeMangaDao(db: AppDatabase): HomeMangaDao = db.homeMangaDao()
}