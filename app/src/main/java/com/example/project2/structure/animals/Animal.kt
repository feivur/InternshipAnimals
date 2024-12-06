package com.example.project2.structure.animals

import android.os.Parcelable//для передачи между компонентами приложения
import com.example.project2.screens.animals.selection.AnimalType

interface Animal : Parcelable, Speaker {
    val id: Long // Новый ID
    val name: String
    val color: String
    val type: AnimalType

    fun describe(): String
}

