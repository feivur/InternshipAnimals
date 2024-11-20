package com.example.project2.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.project2.db.AnimalsEntity
import com.example.project2.db.RoomDB
import com.example.project2.screens.AnimalForm
import com.example.project2.screens.AnimalType

class AnimalsViewModel(application: Application) : AndroidViewModel(application) {
    val animalList: LiveData<List<AnimalsEntity>>
    private val repository: AnimalRepository
    var animalName by mutableStateOf("")
    var animalColor by mutableStateOf("")
    var animalType by mutableStateOf(AnimalType.Cat) // Изменяем тип по умолчанию
    var animalForm by mutableStateOf(AnimalForm.Mammal) // Изменяем форму по умолчанию
    var selectedAnimalIds by mutableStateOf(emptyList<Long>()) // Для выбранных животных

    init {
        val animalsDao = RoomDB.getInstance(application).animalsDao()
        repository = AnimalRepository(animalsDao)
        animalList = repository.animalList
    }

    // Управление состоянием ввода
    fun changeName(value: String) {
        animalName = value
    }

    fun changeColor(value: String) {
        animalColor = value
    }

    // Добавление животного
    fun addAnimal() {
        repository.insertAnimal(
            AnimalsEntity(
                name = animalName,
                color = animalColor,
                type = animalType, // Передаем тип животного
                form = animalForm // Передаем форму животного
            )
        )
    }

    // Удаление выбранных животных
    fun deleteSelectedAnimals() {
        repository.deleteAnimals(selectedAnimalIds)
        selectedAnimalIds = emptyList() // Сбросить выбор
    }
}
