package com.example.project2.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2.structure.Animal
import kotlinx.coroutines.launch

class AnimalsViewModel(application: Application) : AndroidViewModel(application) {

    //private val animalRepository: AnimalRepository
    // val allAnimals: LiveData<List<Animal>> // Данные для отображения в интерфейсе

    init {
        //  val animalDao = AnimalDatabase.getDatabase(application).animalDao()
        //animalRepository = AnimalRepository(animalDao)
        //   allAnimals = animalRepository.allAnimals
    }

    // Метод для добавления животного
    fun addAnimal(animal: Animal) {
        viewModelScope.launch {
            //   animalRepository.insert(animal)
        }
    }

    // Метод для удаления животного
    fun removeAnimal(animal: Animal) {
        viewModelScope.launch {
            //  animalRepository.delete(animal)
        }
    }
}
