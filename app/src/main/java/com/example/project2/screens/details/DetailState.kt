package com.example.project2.screens.details

import com.example.project2.structure.Animal

data class DetailState(
    val animals: List<Animal> = emptyList(),
    val name: String = "",
    val color: String = ""
)