package com.example.project2.functions

import androidx.lifecycle.LifecycleCoroutineScope
import com.example.project2.db.AnimalsDao
import com.example.project2.db.AnimalsEntity
import com.example.project2.structure.Animal
import com.example.project2.structure.Cat
import com.example.project2.structure.Dog
import com.example.project2.structure.Frog
import com.example.project2.structure.Triton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun addAnimal(
    animal: Animal,
    animalsDao: AnimalsDao,
    lifecycleScope: LifecycleCoroutineScope
) {
    val animalEntity = AnimalsEntity(
        name = animal.name,
        color = animal.color,
        type = when (animal) {
            is Cat -> "Cat"
            is Dog -> "Dog"
            is Frog -> "Frog"
            is Triton -> "Triton"
            else -> "Unknown"
        }
    )

    lifecycleScope.launch(Dispatchers.IO) {
        animalsDao.insertAnimal(animalEntity)
    }
}