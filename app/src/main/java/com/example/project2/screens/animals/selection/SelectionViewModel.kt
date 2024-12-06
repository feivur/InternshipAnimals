package com.example.project2.screens.animals.selection

import androidx.lifecycle.ViewModel
import com.example.project2.App
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SelectionViewModel() : ViewModel() {

    private val repository = App.animalsRepository!!

    private val _state = MutableStateFlow(SelectionState())
    val state: StateFlow<SelectionState> = _state

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
//
//    // Добавление животного через репозиторий
//    fun addAnimal() {
//        val animal = when (state.selectedAnimal) {
//            AnimalType.Cat -> Cat(
//                id = System.currentTimeMillis(),
//                name = state.name,
//                color = state.color
//            )
//
//            AnimalType.Dog -> Dog(
//                id = System.currentTimeMillis(),
//                name = state.name,
//                color = state.color
//            )
//
//            AnimalType.Frog -> Frog(
//                id = System.currentTimeMillis(),
//                name = state.name,
//                color = state.color
//            )
//
//            AnimalType.Triton -> Triton(
//                id = System.currentTimeMillis(),
//                name = state.name,
//                color = state.color
//            )
//            else -> null
//        }
//        animal?.let {
//            viewModelScope.launch {
//                repository.addAnimal(it)
//            }}}


}
