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
    private var animalName by mutableStateOf("")
    private var animalColor by mutableStateOf("")
    var animalType by mutableStateOf(AnimalType.Cat)
    var animalForm by mutableStateOf(AnimalForm.Mammal)
    var selectedAnimalIds by mutableStateOf(emptyList<Long>())

    init {
        val animalsDao = RoomDB.getInstance(application).animalsDao()
        repository = AnimalRepository(animalsDao)
        animalList = repository.animalList
    }

    fun changeName(value: String) {
        animalName = value
    }

    fun changeColor(value: String) {
        animalColor = value
    }

    // добавление животного
    fun addAnimal() {
        repository.insertAnimal(
            AnimalsEntity(
                name = animalName,
                color = animalColor,
                type = animalType,
                form = animalForm
            )
        )
    }

    // удаление выбранных животных
    fun deleteSelectedAnimals() {
        repository.deleteAnimals(selectedAnimalIds)
        selectedAnimalIds = emptyList()
    }
}