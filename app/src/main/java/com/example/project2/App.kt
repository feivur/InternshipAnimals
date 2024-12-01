package com.example.project2

import android.app.Application
import androidx.room.Room
import com.example.project2.db.RoomDB
import com.example.project2.utils.AnimalsRepository

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
        animalsRepository = AnimalsRepository(db.animalsDao())
    }

    companion object {
        // Ссылка на DAO
        //todo выпилить +

        var animalsRepository: AnimalsRepository? = null
            private set

    }
}