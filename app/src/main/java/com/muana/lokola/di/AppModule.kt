package com.muana.lokola.di

import android.content.Context
import androidx.room.Room
import com.muana.lokola.data.local.LessonDao
import com.muana.lokola.data.local.LokolaDatabase
import com.muana.lokola.util.DataSaverManager
import com.muana.lokola.util.LanguageManager
import com.muana.lokola.util.OnboardingManager
import com.muana.lokola.util.WallpaperManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LokolaDatabase {
        return Room.databaseBuilder(
            context,
            LokolaDatabase::class.java,
            "lokola_database"
        ).build()
    }

    @Provides
    fun provideLessonDao(database: LokolaDatabase): LessonDao {
        return database.lessonDao()
    }

    @Provides
    @Singleton
    fun provideLanguageManager(@ApplicationContext context: Context): LanguageManager {
        return LanguageManager(context)
    }

    @Provides
    @Singleton
    fun provideOnboardingManager(@ApplicationContext context: Context): OnboardingManager {
        return OnboardingManager(context)
    }

    @Provides
    @Singleton
    fun provideDataSaverManager(@ApplicationContext context: Context): DataSaverManager {
        return DataSaverManager(context)
    }

    @Provides
    @Singleton
    fun provideWallpaperManager(@ApplicationContext context: Context): WallpaperManager {
        return WallpaperManager(context)
    }
}
