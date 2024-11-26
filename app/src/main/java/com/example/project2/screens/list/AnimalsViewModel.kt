package com.example.project2.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2.App
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AnimalsViewModel() : ViewModel() {

    private val animalsDao = App.animalsDao!!

    private val _state = MutableStateFlow(AnimalsState())
    val state: StateFlow<AnimalsState> = _state

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


//    fun addAnimal(animal: Animal) {
//        viewModelScope.launch {
//            val entity = AnimalsEntity(
//                type = animal.type,
//                name = animal.name,
//                color = animal.color
//            )
//            animalsDao.insertAnimal(entity)
//            Log.d("SelectionViewModel", "Animal added: $animal")
//            loadAnimals()  // Загружаем список животных
//            clearAnimalForm()  // Очищаем поля формы
//        }
//    }

    //    private fun clearAnimalForm() {
//        _state.value = _state.value.copy(
//            name = "",
//            color = "",
//            selectedType = AnimalType.Mammal,
//            selectedAnimal = AnimalType.Cat
//        )
//    }
    fun deleteSelectedAnimals(ids: List<Long>) {
        viewModelScope.launch {
            animalsDao.deleteAnimals(ids)
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

//    fun showAnimalSelectionDialog(show: Boolean) {
//        _state.value = _state.value.copy(
//            showAnimalSelectionDialog = show
//        )
//    }

    fun clearSelectedAnimals() {
        _state.value = _state.value.copy(
            selectedAnimalIds = emptySet(),
            deleteMode = false
        )
    }
}
//
