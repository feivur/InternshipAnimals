//package com.example.project2.viewmodel
//
//import androidx.lifecycle.LiveData
//import com.example.project2.db.animals.AnimalsDao
//import com.example.project2.db.animals.AnimalsEntity
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//class AnimalRepository(private val animalsDao: AnimalsDao) {
//    private val coroutineScope = CoroutineScope(Dispatchers.Main)
//
//    val animalList: LiveData<List<AnimalsEntity>> = animalsDao.getAllAnimals()
//
//    //добавление животного
//    fun insertAnimal(animal: AnimalsEntity) {
//        coroutineScope.launch(Dispatchers.IO) {
//            animalsDao.insertAnimal(animal)
//        }
//    }
//
//    //удаление животных по ID
//    fun deleteAnimals(selectedIds: List<Long>) {
//        coroutineScope.launch(Dispatchers.IO) {
//            animalsDao.deleteAnimals(selectedIds)
//        }
//    }
//
//    fun getAllAnimals(): LiveData<List<AnimalsEntity>> {
//        return animalsDao.getAllAnimals()
//    }
//}
