package com.example.project2

import android.util.Log
import kotlinx.parcelize.Parcelize
@Parcelize
class Triton(override val name: String, override val color: String) : Reptile(name, color), Speaker {
    override fun crawl() {
        Log.d("AnimalAction", "$name triton is crawling")
    }

    override fun shedSkin() {
        Log.d("AnimalAction", "$name triton is shedding its skin")
    }

    override fun speak(): String {
        Log.d("AnimalAction", "$name triton says trrr")
        return "$name triton says trrr"
    }

    override fun describe(): String {
        return super.describe() + " It's a triton."
    }
}
