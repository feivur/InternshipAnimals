package com.example.project2.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.project2.db.AnimalsDao
import com.example.project2.db.AnimalsEntity
import com.example.project2.db.RoomDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimalsViewModel(application: Application, private val animalsDao: AnimalsDao) :
    AndroidViewModel(application) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    val animalList: LiveData<List<AnimalsEntity>> //=
    private val repository: AnimalRepository
    var animalForm by mutableStateOf("")
    var animalType by mutableStateOf("")
    var animalName by mutableStateOf("")
    var animalColor by mutableStateOf("")


    init {
        val animalDb = RoomDB.getInstance(application)
        val animalsDao = animalDb.animalsDao()
        repository = AnimalRepository(animalsDao)
        animalList = repository.animalList

        loadAnimals()
    }

    //для наблюдения за списком животных
    val allAnimals: LiveData<List<AnimalsEntity>> = animalsDao.getAllAnimals()

    //для добавления животного
    fun addAnimal(animal: AnimalsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            animalsDao.insertAnimal(animal)
        }
    }

    //для удаления животных
    fun deleteAnimals(selectedIds: List<Long>) {
        viewModelScope.launch(Dispatchers.IO) {
            animalsDao.deleteAnimals(selectedIds)
        }
    }

    //на случай пустой бд
    private fun loadAnimals() {
        //флаг загрузки
        _isLoading.value = true
        viewModelScope.launch {
            val animals = animalsDao.getAllAnimals()
            _isLoading.value = false
        }
    }
}
