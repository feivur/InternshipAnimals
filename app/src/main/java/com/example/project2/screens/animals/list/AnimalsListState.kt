package com.example.project2.screens.animals.list

import com.example.project2.structure.animals.Animal

data class AnimalsListState(
    val animals: List<Animal> = emptyList(),//
    val selectedAnimalIds: Set<Long> = emptySet(),
    val deleteMode: Boolean = false,

    )
