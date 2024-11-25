package com.example.project2.structure

import android.util.Log
//import com.example.project2.screens.AnimalForm
import com.example.project2.screens.selection.AnimalType
import kotlinx.parcelize.Parcelize
@Parcelize
class Dog(override val name: String, override val color: String, override val id: Long) :
    Mammal(name, color), Speaker {
    override val type: AnimalType = AnimalType.Dog

    //   override val form: AnimalForm = AnimalForm.Mammal
    override fun run() {
        Log.d("AnimalAction", "$name dog is running")
    }

    override fun jump() {
        Log.d("AnimalAction", "$name dog is jumping")
    }

    override fun speak(): String {
        Log.d("AnimalAction", "$name dog says woof")
        return "$name dog says woof"
    }

    override fun describe(): String {
        return super.describe() + " It's a dog."
    }

}
