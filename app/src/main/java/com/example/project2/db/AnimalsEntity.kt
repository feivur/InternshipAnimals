package com.example.project2.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animals")
data class AnimalsEntity(

    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    @ColumnInfo(name = "animal_form") val form: String,
    @ColumnInfo(name = "animal_type") val type: String, //todo а как же AnimalType?
//todo тут нужно использовать enum из AnimalSelectionScreen: enum class AnimalForm {
//todo   Mammal, Reptile
//todo  }
//todo enum class AnimalType {
//todo     Cat, Dog, Frog, Triton
//todo  }
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "color") val color: String?
)
