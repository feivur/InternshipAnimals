package com.example.project2

import android.util.Log
import kotlinx.parcelize.Parcelize

@Parcelize
class Cat(override val name: String, override val color: String) : Mammal(name, color), Speaker {
    override fun run() {
        Log.d("AnimalAction", "$name cat is running")
    }

    override fun jump() {
        Log.d("AnimalAction", "$name cat is jumping")
    }

    override fun speak(): String {
        Log.d("AnimalAction", "$name cat says meow")
        return "$name cat says meow"
    }

    override fun describe(): String {
        return super.describe() + " It's a cat."
    }

}
