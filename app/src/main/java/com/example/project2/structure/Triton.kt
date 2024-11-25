package com.example.project2.structure

import android.util.Log
//import com.example.project2.screens.AnimalForm
import com.example.project2.screens.AnimalType
import kotlinx.parcelize.Parcelize
@Parcelize
class Triton(override val name: String, override val color: String, override val id: Long) :
    Reptile(name, color),

    Speaker {
    override val type: AnimalType = AnimalType.Triton

    // override val form: AnimalForm = AnimalForm.Reptile
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
