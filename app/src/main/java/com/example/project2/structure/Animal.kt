package com.example.project2.structure

import android.os.Parcelable//для передачи между компонентами приложения
import com.example.project2.screens.AnimalType

interface Animal : Parcelable, Speaker {
    val id: Long // Новый ID
    val name: String
    val color: String
    val type: AnimalType

    fun describe(): String
}

