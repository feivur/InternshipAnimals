package com.example.project2.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AnimalsDao {
    @Insert
    fun insertAll(vararg animals: AnimalsEntity)

    @Delete
    fun delete(animals: AnimalsEntity)

    @Query("SELECT * FROM animals")
    fun getAll(): List<AnimalsEntity>
}