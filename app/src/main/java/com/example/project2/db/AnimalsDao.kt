package com.example.project2.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AnimalsDao {
    // для добавления
//    @Insert
//    suspend fun insertAll(vararg animals: AnimalsEntity)

    @Insert
    suspend fun insertAnimal(animal: AnimalsEntity) // addanimal

    @Query("DELETE FROM animals WHERE id IN (:ids)")
    suspend fun deleteAnimals(ids: List<Long>)
//    @Delete
//    suspend fun delete(animals: List<AnimalsEntity>)

//    // удаления одного элемента
//    @Delete
//    suspend fun delete(animals: AnimalsEntity)

    // получение всех животных через lifedata
    @Query("SELECT * FROM animals")
    fun getAllAnimals(): LiveData<List<AnimalsEntity>>
//    @Query("SELECT * FROM animals")
//    suspend fun getAllAnimals(): List<AnimalsEntity>
}