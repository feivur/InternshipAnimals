package com.example.project2.screens.selection

import com.example.project2.structure.Animal

data class SelectionState(
    val animals: List<Animal> = emptyList(),
    val name: String = "",
    val color: String = "",
    val selectedType: AnimalType = AnimalType.Mammal,
    val selectedAnimal: AnimalType = AnimalType.Cat,
    val showAnimalSelectionDialog: Boolean = false
)
