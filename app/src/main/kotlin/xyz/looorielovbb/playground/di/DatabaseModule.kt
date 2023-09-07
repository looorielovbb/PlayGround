package xyz.looorielovbb.playground.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xyz.looorielovbb.playground.data.local.AppDatabase
import xyz.looorielovbb.playground.data.local.ArticleDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "playground.db"
        ).build()
    }

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): ArticleDao {
        return appDatabase.articleDao()
    }
}