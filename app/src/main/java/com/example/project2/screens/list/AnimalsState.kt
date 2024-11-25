package com.example.project2.screens.list

import com.example.project2.screens.selection.AnimalType
import com.example.project2.structure.Animal

data class AnimalsState(
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
