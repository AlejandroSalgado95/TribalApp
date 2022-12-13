package com.example.tribalapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tribalapp.data.local.entity.JokeEntity
import com.example.tribalapp.data.remote.dto.JokeDTO

@Dao
interface TribalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(joke: JokeEntity)

    @Query("SELECT * FROM joke_table WHERE category LIKE '%' || :category || '%'")
    fun getJokesOfCategoryFromDB(category : String): LiveData<List<JokeEntity>>

}
