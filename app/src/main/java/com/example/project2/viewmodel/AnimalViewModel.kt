package com.example.project2.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2.db.AnimalsDao
import com.example.project2.db.AnimalsEntity
import com.example.project2.screens.AnimalType
import com.example.project2.structure.Animal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AnimalViewModel(private val animalsDao: AnimalsDao) : ViewModel() {

    private val _state = MutableStateFlow(AnimalState())
    val state: StateFlow<AnimalState> get() = _state

    init {
        loadAnimals()
    }

    private fun loadAnimals() {
        viewModelScope.launch {
            val entities = animalsDao.getAllAnimals().value.orEmpty()
            Log.d("AnimalViewModel", "Loaded animals: $entities")
            _state.value = _state.value.copy(
                animals = entities.map { it.toAnimal() }
            )
        }//
    }

    fun addAnimal(animal: Animal) {
        viewModelScope.launch {
            val entity = AnimalsEntity(
                type = animal.type,
                name = animal.name,
                color = animal.color
            )
            animalsDao.insertAnimal(entity)
            Log.d("AnimalViewModel", "Animal added: $animal")
            loadAnimals()  // Загружаем список животных
            clearAnimalForm()  // Очищаем поля формы
        }
    }

    //        fun addAnimal(animal: Animal) {
//            viewModelScope.launch {
//                val entity = AnimalsEntity(
//                    type = animal.type,
//                    name = animal.name,
//                    color = animal.color
//                )
//                animalsDao.insertAnimal(entity)
//                // После добавления обновляем список животных
//                val entities = animalsDao.getAllAnimals().value.orEmpty()
//                _state.value = _state.value.copy(
//                    animals = entities.map { it.toAnimal() }
//
//                )
//            }
//        }
    private fun clearAnimalForm() {
        _state.value = _state.value.copy(
            name = "",
            color = "",
            selectedType = AnimalType.Mammal,
            selectedAnimal = AnimalType.Cat
        )
    }

    fun deleteSelectedAnimals(ids: List<Long>) {
        viewModelScope.launch {
            animalsDao.deleteAnimals(ids)
            loadAnimals()
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

    fun showAnimalSelectionDialog(show: Boolean) {
        _state.value = _state.value.copy(
            showAnimalSelectionDialog = show
        )
    }

    fun clearSelectedAnimals() {
        _state.value = _state.value.copy(
            selectedAnimalIds = emptySet(),
            deleteMode = false
        )
    }
}
//
