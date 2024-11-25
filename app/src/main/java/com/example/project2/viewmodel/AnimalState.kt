package com.example.project2.viewmodel

import com.example.project2.screens.AnimalType
import com.example.project2.structure.Animal

data class AnimalState(
    val animals: List<Animal> = emptyList(),/////
    val isLoading: Boolean = false,
    val selectedAnimalIds: Set<Long> = emptySet(),
    val deleteMode: Boolean = false,
    val showAnimalSelectionDialog: Boolean = false,
    val selectedType: AnimalType = AnimalType.Mammal,
    val selectedAnimal: AnimalType = AnimalType.Cat,
    val name: String = "",
    val color: String = ""
)
