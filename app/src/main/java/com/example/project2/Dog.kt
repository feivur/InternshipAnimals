package com.example.project2

import android.util.Log
import kotlinx.parcelize.Parcelize
@Parcelize
class Dog(override val name: String, override val color: String) : Mammal(name, color), Speaker {
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
