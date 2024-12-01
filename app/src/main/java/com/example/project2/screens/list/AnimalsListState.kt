package com.example.project2.screens.list

import com.example.project2.structure.Animal

data class AnimalsListState(
    val animals: List<Animal> = emptyList(),//
    val selectedAnimalIds: Set<Long> = emptySet(),
    val deleteMode: Boolean = false,

    )
