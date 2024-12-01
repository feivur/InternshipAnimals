package com.example.project2.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimalsDao {
    @Insert
    suspend fun insertAnimal(animal: AnimalsEntity) // addanimal

    @Query("DELETE FROM animals WHERE animalId IN (:ids)")
    suspend fun deleteAnimals(ids: List<Long>)

    @Query("SELECT * FROM animals")
    fun getAllAnimals(): Flow<List<AnimalsEntity>>//LiveData //Flow

    @Query("DELETE FROM animals WHERE animalId = :id")
    fun deleteAnimal(id: Int)

    @Query("SELECT * FROM animals WHERE animalId = :id")
    fun get(id: Int): AnimalsEntity

}