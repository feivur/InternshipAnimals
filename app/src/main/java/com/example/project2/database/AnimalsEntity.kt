package com.example.project2.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.project2.AnimalType
import com.example.project2.structure.Animal

@Entity(tableName = "animals")
data class AnimalsEntity(

    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "animal_type") val animalType: AnimalType,
    @ColumnInfo(name = "animal") val animal: Animal,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "color") val color: String?,
)
