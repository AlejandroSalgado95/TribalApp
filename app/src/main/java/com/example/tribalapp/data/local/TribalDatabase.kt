package com.example.tribalapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tribalapp.data.local.entity.JokeEntity

@Database(
    entities = [JokeEntity::class],
    version = 2
)

abstract class TribalDatabase : RoomDatabase() {
    abstract fun getTribalDao(): TribalDao
}