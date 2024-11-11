package com.example.project2

import android.util.Log
import kotlinx.parcelize.Parcelize
@Parcelize
class Frog(override val name: String, override val color: String) : Reptile(name, color), Speaker {
    override fun crawl() {
        Log.d("AnimalAction", "$name frog is crawling")
    }

    override fun shedSkin() {
        Log.d("AnimalAction", "$name frog is shedding its skin")
    }

    override fun speak(): String {
        Log.d("AnimalAction", "$name frog says kva")
        return "$name frog says kva"
    }

    override fun describe(): String {
        return super.describe() + " It's a frog."
    }
}
