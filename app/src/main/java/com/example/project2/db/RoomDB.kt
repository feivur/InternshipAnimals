package com.example.project2.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters
@Database(entities = [(AnimalsEntity::class)], version = 10)
abstract class RoomDB : RoomDatabase() {
    abstract fun animalsDao(): AnimalsDao

    // реализуем синглтон
//    companion object {
//        private var INSTANCE: RoomDB? = null
////        fun getInstance(context: Context): RoomDB {
////
////            synchronized(this) {
////                var instance = INSTANCE
////                if (instance == null) {
////                    instance = Room.databaseBuilder(
////                        context.applicationContext,
////                        RoomDB::class.java,
////                        "animalsdb"
////                    ).fallbackToDestructiveMigration().build()
////                    INSTANCE = instance
////                }
////                return instance
////            }
////
////
////        }
//    }
}
