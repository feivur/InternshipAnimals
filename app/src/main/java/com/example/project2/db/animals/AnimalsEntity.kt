package com.example.project2.db.animals

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.project2.screens.animals.selection.AnimalType
import com.example.project2.structure.animals.Animal
import com.example.project2.structure.animals.Cat
import com.example.project2.structure.animals.Dog
import com.example.project2.structure.animals.Frog
import com.example.project2.structure.animals.Triton


@Entity(tableName = "animals")
class AnimalsEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "animalId")
    var id: Long = 0

    @ColumnInfo(name = "animalType")
    var type: AnimalType = AnimalType.Cat

    @ColumnInfo(name = "animalName")
    var name: String = ""

    @ColumnInfo(name = "animalColor")
    var color: String = ""

    constructor()

    constructor(type: AnimalType, name: String, color: String) {
        this.type = type
        this.name = name
        this.color = color
    }

    fun toAnimal(): Animal {
        return when (this.type) {
            AnimalType.Cat -> Cat(id = this.id, name = this.name, color = this.color)
            AnimalType.Dog -> Dog(id = this.id, name = this.name, color = this.color)
            AnimalType.Frog -> Frog(id = this.id, name = this.name, color = this.color)
            AnimalType.Triton -> Triton(id = this.id, name = this.name, color = this.color)
            else -> throw IllegalArgumentException("Unsupported animal type")
        }
    }

}
