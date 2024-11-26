package com.example.project2.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2.App
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel() : ViewModel() {
    private val animalsDao = App.animalsDao!!

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state

    init {
        loadAnimals()
    }

    private fun loadAnimals() {
        viewModelScope.launch {
            animalsDao.getAllAnimals().collect { entities ->
                _state.value = _state.value.copy(
                    animals = entities.map { it.toAnimal() }
                )
            }
        }
    }
}
