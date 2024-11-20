package com.example.project2.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.project2.screens.AnimalForm
import com.example.project2.screens.AnimalType
import com.example.project2.structure.Animal
import com.example.project2.structure.Cat
import com.example.project2.structure.Dog
import com.example.project2.structure.Frog
import com.example.project2.structure.Triton
@Entity(tableName = "animals")
class AnimalsEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "animalId")
    var id: Long = 0

    @ColumnInfo(name = "animalForm")
    var form: AnimalForm = AnimalForm.Mammal

    @ColumnInfo(name = "animalType")
    var type: AnimalType = AnimalType.Cat // Здесь по умолчанию

    @ColumnInfo(name = "animalName")
    var name: String = ""

    @ColumnInfo(name = "animalColor")
    var color: String = ""

    constructor()

    constructor(form: AnimalForm, type: AnimalType, name: String, color: String) {
        this.form = form
        this.type = type
        this.name = name
        this.color = color
    }

    // Преобразование в Animal
    fun toAnimal(): Animal {
        return when (this.type) {
            AnimalType.Cat -> Cat(name, color)
            AnimalType.Dog -> Dog(name, color)
            AnimalType.Frog -> Frog(name, color)
            AnimalType.Triton -> Triton(name, color)
        }
    }
}
