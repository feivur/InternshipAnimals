package com.example.project2.screens.animals.details

import com.example.project2.structure.animals.Animal

data class DetailState(
    val animal: Animal? = null,
    val name: String = "",
    val color: String = ""
)