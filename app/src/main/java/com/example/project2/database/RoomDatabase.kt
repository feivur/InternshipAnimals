package com.example.project2.database

import androidx.room.Database

@Database(entities = [AnimalsEntity::class], version = 1)
abstract class RoomDatabase {
    abstract fun animalsDao(): AnimalsDao
}