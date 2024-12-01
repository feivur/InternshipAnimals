package com.example.project2.screens.selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2.App
import com.example.project2.utils.AnimalsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SelectionViewModel() : ViewModel() {

    private val repository: AnimalsRepository = App.animalsRepository!!

    private val _state = MutableStateFlow(SelectionState())
    val state: StateFlow<SelectionState> = _state

    init {
        loadAnimals()
    }

    private fun loadAnimals() {
        viewModelScope.launch {
            repository.list().collect { animals ->
                _state.value = _state.value.copy(
                    animals = animals
                )
            }
        }
    }

    fun setSelectedType(type: AnimalType) {
        _state.value = _state.value.copy(
            selectedType = type
        )
    }

    fun setSelectedAnimal(animal: AnimalType) {
        _state.value = _state.value.copy(
            selectedAnimal = animal
        )
    }

    fun setName(name: String) {
        _state.value = _state.value.copy(
            name = name
        )
    }

    fun setColor(color: String) {
        _state.value = _state.value.copy(
            color = color
        )
    }
    fun clearForm() {
        _state.value = _state.value.copy(
            name = "",
            color = "",
            selectedType = AnimalType.Mammal,
            selectedAnimal = AnimalType.Cat
        )
    }

}