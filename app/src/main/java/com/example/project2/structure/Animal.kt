package com.example.project2.structure

import android.os.Parcelable//для передачи между компонентами приложения

interface Animal : Parcelable, Speaker {
    val name: String
    val color: String

    fun describe(): String
    override fun speak(): String
}
