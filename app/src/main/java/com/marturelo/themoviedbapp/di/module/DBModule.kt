package com.marturelo.themoviedbapp.di.module

import android.content.Context
import androidx.room.Room
import com.marturelo.themoviedbapp.data.db.TMDBDatabase
import com.marturelo.themoviedbapp.data.db.dao.MovieDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule {
    @Singleton
    @Provides
    fun providesRoomDatabase(context: Context): TMDBDatabase {
        return Room.databaseBuilder(
            context,
            TMDBDatabase::class.java,
            "movies_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesMovieDAO(db: TMDBDatabase): MovieDao = db.movieDao()
}