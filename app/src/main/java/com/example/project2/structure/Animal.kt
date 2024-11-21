package com.example.project2.structure

import android.os.Parcelable//для передачи между компонентами приложения
//import com.example.project2.screens.AnimalForm
import com.example.project2.screens.AnimalType

interface Animal : Parcelable, Speaker {

    val name: String
    val color: String
    val type: AnimalType // Добавляем тип животного

    // val form: AnimalForm
    fun describe(): String
    override fun speak(): String
}
