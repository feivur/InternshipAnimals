package com.example.project2.utils.animalsStub

import com.example.project2.db.animals.AnimalsDao
import com.example.project2.db.animals.AnimalsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class StubAnimalsDao : AnimalsDao {

    override fun getAllAnimals(): Flow<List<AnimalsEntity>> {
        return flowOf(emptyList())
    }

    override suspend fun insertAnimal(animal: AnimalsEntity) {}

    override suspend fun deleteAnimals(ids: List<Long>) {}

    override suspend fun get(id: Long): AnimalsEntity {
        throw UnsupportedOperationException("StubAnimalsDao does not support this operation")
    }
}
