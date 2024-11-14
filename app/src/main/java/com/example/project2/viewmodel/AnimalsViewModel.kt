package com.example.project2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.project2.db.AnimalsDao
import com.example.project2.db.AnimalsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimalsViewModel(application: Application, private val animalsDao: AnimalsDao) :
    AndroidViewModel(application) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
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
