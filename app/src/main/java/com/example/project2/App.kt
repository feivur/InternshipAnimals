package com.example.project2

import android.app.Application
import androidx.room.Room
import com.example.project2.db.AnimalsDao
import com.example.project2.db.RoomDB

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        animalsDao = Room.databaseBuilder(
            applicationContext,
            RoomDB::class.java, "animals_db"
        )
            //.fallbackToDestructiveMigration()
            .build().animalsDao()
    }

    companion object {
        //var instaApp
        var animalsDao: AnimalsDao? = null
            private set
    }
}