package com.example.project2.utils.animalsStub

import com.example.project2.screens.animals.selection.AnimalType
import com.example.project2.structure.animals.Animal
import com.example.project2.utils.AnimalsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

//заглушка
class StubAnimalsRepository : AnimalsRepository(animalsDao = StubAnimalsDao()) {

    override suspend fun insert(animal: Animal) {
        // ничего не делает
    }

    override suspend fun delete(ids: List<Long>) {
        // ничего не делает
    }

    override fun list(): Flow<List<Animal>> {
        return flowOf(emptyList()) // возвращает пустой список
    }

    override suspend fun get(id: Long): Animal {
        return AnimalImpl(id, "Stub", "Stub", AnimalType.Mammal) // возвращает тестовый объект
    }
}
