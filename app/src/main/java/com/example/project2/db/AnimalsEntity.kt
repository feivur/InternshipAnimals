package com.example.project2.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animals")
data class AnimalsEntity(

    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "animal_type") val type: String?,
//    @ColumnInfo(name = "animal") val animal: Animal,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "color") val color: String?,
)
