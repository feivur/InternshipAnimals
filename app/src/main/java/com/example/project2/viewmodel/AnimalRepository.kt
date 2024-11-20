package com.example.project2.viewmodel

import androidx.lifecycle.LiveData
import com.example.project2.db.AnimalsDao
import com.example.project2.db.AnimalsEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimalRepository(private val animalsDao: AnimalsDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val animalList: LiveData<List<AnimalsEntity>> = animalsDao.getAllAnimals()

    fun insertAnimal(animalsEntity: AnimalsEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            animalsDao.insertAnimal(animalsEntity)
        }
    }

}