package com.example.project2.db.animals

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
    fun getAllAnimals(): Flow<List<AnimalsEntity>>//todo flow<Animals...>
    //get 1
    @Query("SELECT * FROM animals WHERE animalId = :id")
    suspend fun get(id: Long): AnimalsEntity//suspent add for stub

}
