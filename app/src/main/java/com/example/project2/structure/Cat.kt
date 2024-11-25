package com.example.project2.structure

import android.util.Log
import com.example.project2.screens.AnimalType
import kotlinx.parcelize.Parcelize

@Parcelize
class Cat(override val name: String, override val color: String, override val id: Long) :
    Mammal(name, color), Speaker {
    override val type: AnimalType = AnimalType.Cat

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

//
//@Parcelize
//class Cat(
//    override val id: Long, // Новый ID
//    override val name: String,
//    override val color: String
//) : Animal {
//    override val type: AnimalType = AnimalType.Cat
//
//    override fun describe(): String {
//        return "$name is a $color cat."
//    }
//}