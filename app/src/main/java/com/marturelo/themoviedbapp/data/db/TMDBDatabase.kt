package com.marturelo.themoviedbapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marturelo.themoviedbapp.data.db.dao.MovieDao
import com.marturelo.themoviedbapp.data.model.MovieModel

@Database(entities = [MovieModel::class], version = 1, exportSchema = false)
abstract class TMDBDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}