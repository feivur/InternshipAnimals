package com.example.project2.utils

import androidx.room.Insert
import androidx.room.Query
import com.example.project2.db.AnimalsDao
import com.example.project2.db.AnimalsEntity
import com.example.project2.structure.Animal
import kotlinx.coroutines.flow.Flow

class AnimalsRepository(
    private val animalsDao: AnimalsDao
) {
    suspend fun insert(animal: Animal){
        val entity = AnimalsEntity(
            type = animal.type,
            name = animal.name,
            color = animal.color
        )
        animalsDao.insertAnimal(entity)
    }

    fun delete(ids: List<Long>){
        TODO("")

    }

    fun list(): Flow<List<Animal>>{
        TODO("")
    }

    fun delete(id: Int){
        TODO("")

    }

    fun get(id: Int): Animal{
        TODO("")

    }
}