package com.example.project2

import android.util.Log

class Dog(name: String, color: String) : Mammal(name, color), Speaker {
    override fun run() {
        Log.d("AnimalAction", "$name the dog is running")
    }

    override fun jump() {
        Log.d("AnimalAction", "$name the dog is jumping")
    }

    override fun speak() {
        Log.d("AnimalAction", "$name dog says woof")
    }

    override fun describe() {
        super.describe()
        Log.d("AnimalDescription", "$name the $color dog")
    }
}
