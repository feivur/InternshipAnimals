package com.example.project2.screens.details

import com.example.project2.structure.Animal

data class DetailState(
    val animal: Animal? = null,
    val name: String = "",
    val color: String = ""
)