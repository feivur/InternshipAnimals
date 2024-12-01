package com.example.project2.screens.selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2.App
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SelectionViewModel() : ViewModel() {

    private val animalsDao = App.animalsDao!!

    private val _state = MutableStateFlow(SelectionState())
    val state: StateFlow<SelectionState> = _state

    init {
        loadAnimals()
    }

    //todo remove
    private fun loadAnimals() {
        viewModelScope.launch {
            animalsDao.getAllAnimals().collect { entities ->
                _state.value = _state.value.copy(
                    animals = entities.map { it.toAnimal() }
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


}