package com.example.project2.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.project2.screens.AnimalForm
import com.example.project2.screens.AnimalType

@Entity(tableName = "animals")
class AnimalsEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "animalId")
    var id: Long = 0

    @ColumnInfo(name = "animalForm")
    var form: AnimalForm = AnimalForm.Mammal

    @ColumnInfo(name = "animalType")
    var type: AnimalType = AnimalType.Cat //todo а как же AnimalType? -готово?

    //todo тут нужно использовать enum из AnimalSelectionScreen:
//todo enum class AnimalType {
//todo     Cat, Dog, Frog, Triton
//todo  }
//todo enum class AnimalForm {
//todo   Mammal, Reptile
//todo  }
    @ColumnInfo(name = "animalName")
    var name: String = ""
    @ColumnInfo(name = "animalColor")
    var color: String = ""

    constructor() {}
    constructor(form: AnimalForm, type: AnimalType, name: String, color: String) {
        this.form = form
        this.type = type
        this.name = name
        this.color = color
    }
}

