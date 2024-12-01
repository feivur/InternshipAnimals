package com.example.project2.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters
@Database(entities = [(AnimalsEntity::class)], version = 12)
abstract class RoomDB : RoomDatabase() {
    abstract fun animalsDao(): AnimalsDao
}
