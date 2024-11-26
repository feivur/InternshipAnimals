package com.example.project2.screens.selection

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2.App
import com.example.project2.db.AnimalsEntity
import com.example.project2.structure.Animal
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

    private fun loadAnimals() {
        viewModelScope.launch {
            animalsDao.getAllAnimals().collect { entities ->
                _state.value = _state.value.copy(
                    animals = entities.map { it.toAnimal() }
                )
            }
        }
    }

    fun addAnimal(animal: Animal) {
        viewModelScope.launch {
            val entity = AnimalsEntity(
                type = animal.type,
                name = animal.name,
                color = animal.color
            )
            animalsDao.insertAnimal(entity)
            Log.d("SelectionViewModel", "Animal added: $animal")
            loadAnimals()  // Загружаем список животных
            clearAnimalForm()  // Очищаем поля формы
        }
    }

    private fun clearAnimalForm() {
        _state.value = _state.value.copy(
            name = "",
            color = "",
            selectedType = AnimalType.Mammal,
            selectedAnimal = AnimalType.Cat
        )
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

    fun showAnimalSelectionDialog(show: Boolean) {
        _state.value = _state.value.copy(
            showAnimalSelectionDialog = show
        )
    }


}