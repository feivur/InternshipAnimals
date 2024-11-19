package com.example.project2.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animals")
data class AnimalsEntity(

    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    @ColumnInfo(name = "animal_form") val form: String?,
    @ColumnInfo(name = "animal_type") val type: String?, //todo а как же AnimalType?
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "color") val color: String?
)
