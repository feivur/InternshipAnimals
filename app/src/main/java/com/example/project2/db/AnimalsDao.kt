package com.example.project2.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AnimalsDao {
    @Insert
    suspend fun insertAnimal(animal: AnimalsEntity) // addanimal

    @Query("DELETE FROM animals WHERE id IN (:ids)")
    suspend fun deleteAnimals(ids: List<Long>)
    // получение всех животных через lifedata
    @Query("SELECT * FROM animals")
    fun getAllAnimals(): LiveData<List<AnimalsEntity>>
}