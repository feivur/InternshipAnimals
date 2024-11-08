package com.example.project2

import android.util.Log

class Triton(name: String, color: String) : Reptile(name, color), Speaker {
    override fun crawl() {
        Log.d("AnimalAction", "$name triton is crawling")
    }

    override fun shedSkin() {
        Log.d("AnimalAction", "$name triton is shedding its skin")
    }

    override fun speak() {
        Log.d("AnimalAction", "$name triton says trrr")
    }

    override fun describe() {
        super.describe()
        Log.d("AnimalDescription", "$name the $color triton")
    }
}
