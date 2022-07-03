package com.marturelo.themoviedbapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marturelo.themoviedbapp.data.model.MovieModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieModel>)

    @Query("SELECT * FROM movies")
    fun discovery(): Observable<List<MovieModel>>

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :query || '%'")
    fun search(query: String): Observable<List<MovieModel>>


    @Query("SELECT * FROM movies")
    fun getAll(): Observable<List<MovieModel>>
}