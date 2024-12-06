package com.example.project2.utils

import com.example.project2.db.animals.AnimalsDao
import com.example.project2.db.animals.AnimalsEntity
import com.example.project2.structure.animals.Animal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AnimalsRepository(
    private val animalsDao: AnimalsDao
) {
    // Поток для получения списка всех животных
    private val animalList: Flow<List<Animal>> = animalsDao.getAllAnimals()
        .map { entities ->
            entities.map { it.toAnimal() }
        }

    //    suspend fun addAnimal(animal: Animal) {
//        insert(animal)
//    }
    // Добавление животного
    suspend fun insert(animal: Animal) {
        val entity = AnimalsEntity(
            type = animal.type,
            name = animal.name,
            color = animal.color
        )
        animalsDao.insertAnimal(entity)
    }

    // Удаление животных по списку ID
    suspend fun delete(ids: List<Long>) {
        animalsDao.deleteAnimals(ids)
    }

    // Получение списка животных
    fun list(): Flow<List<Animal>> = animalList

    // Получение конкретного животного по ID
    suspend fun get(id: Long): Animal = withContext(Dispatchers.IO) {
        animalsDao.get(id).toAnimal()
    }
}