package com.example.project2

import android.util.Log

class Frog(name: String, color: String) : Reptile(name, color), Speaker {
    override fun crawl() {
        Log.d("AnimalAction", "$name frog is crawling")//ползет
    }

    override fun shedSkin() {
        Log.d("AnimalAction", "$name frog is shedding its skin")//сбрасывает кожу
    }

    override fun speak() {
        Log.d("AnimalAction", "$name frog says kva")
    }

    override fun describe() {
        super.describe()
        Log.d("AnimalDescription", "$name the $color frog")
    }
}
