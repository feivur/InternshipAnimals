package com.example.project2.screens.animals.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2.App
import com.example.project2.structure.animals.Animal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AnimalsListModel() : ViewModel() {

    private val repository = App.animalsRepository!!

    private val _state = MutableStateFlow(AnimalsListState())
    val state: StateFlow<AnimalsListState> = _state

    init {
        loadAnimals()
    }

    private fun loadAnimals() {
        viewModelScope.launch {
            repository.list().collect { animals ->
                _state.value = _state.value.copy(animals = animals)
            }
        }
    }

    fun addAnimal(animal: Animal) {
        viewModelScope.launch {
            repository.insert(animal)
            loadAnimals()
        }
    }


    fun deleteSelectedAnimals(ids: List<Long>) {
        viewModelScope.launch {
            repository.delete(ids)
            loadAnimals()
        }
    }

    fun toggleDeleteMode() {
        _state.value = _state.value.copy(
            deleteMode = !_state.value.deleteMode
        )
    }

    fun selectAnimal(id: Long, isChecked: Boolean) {
        _state.value = _state.value.copy(
            selectedAnimalIds = if (isChecked) {
                _state.value.selectedAnimalIds + id
            } else {
                _state.value.selectedAnimalIds - id
            }
        )
    }

    fun clearSelectedAnimals() {
        _state.value = _state.value.copy(
            selectedAnimalIds = emptySet(),
            deleteMode = false
        )
    }
}
