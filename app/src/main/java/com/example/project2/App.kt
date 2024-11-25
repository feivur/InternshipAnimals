package com.example.project2

import android.app.Application
import androidx.room.Room
import com.example.project2.db.AnimalsDao
import com.example.project2.db.RoomDB

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // Инициализация бд
        val db = Room.databaseBuilder(
            applicationContext,
            RoomDB::class.java, "animals_db"
        )
            .fallbackToDestructiveMigration()
            .build()
        // Сохраняем ссылку на DAO
        animalsDao = db.animalsDao()
    }

    companion object {
        // Ссылка на DAO
        var animalsDao: AnimalsDao? = null
            private set
    }
}