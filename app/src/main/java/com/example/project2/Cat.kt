package com.example.project2

import android.util.Log

class Cat(name: String, color: String) : Mammal(name, color), Speaker {
    override fun run() {
        Log.d("AnimalAction", "$name cat is running")
    }

    override fun jump() {
        Log.d("AnimalAction", "$name cat is jumping")
    }

    override fun speak() {
        Log.d("AnimalAction", "$name cat says meow")
    }

    override fun describe() {
        super.describe()
        Log.d("AnimalDescription", "$name the $color cat")
    }
}
