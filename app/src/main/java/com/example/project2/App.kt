package com.example.project2

import android.app.Application
import android.content.Context
import com.example.project2.utils.AnimalsRepository
import com.example.project2.utils.animalsStub.StubAnimalsRepository

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        animalsRepository = StubAnimalsRepository() // используем заглушку
    }

    companion object {
        lateinit var appContext: Context
            private set

        lateinit var animalsRepository: AnimalsRepository
            private set
    }

}

/* animals
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
        private var _animalsRepository: AnimalsRepository? = null

        val animalsRepository: AnimalsRepository
            get() {
                if (_animalsRepository == null) {
                    throw IllegalStateException("AnimalsRepository не инициализирован!")
                }
                return _animalsRepository!!
            }
    }}
*/