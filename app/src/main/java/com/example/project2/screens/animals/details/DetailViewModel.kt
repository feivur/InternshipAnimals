package com.example.project2.screens.animals.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel() : ViewModel() {
    //  private val animalsDao = App.animalsDao!!//todo remove +

    private val animalsRepository = App.animalsRepository!!

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state

    fun init(animalId: Long) {
        viewModelScope.launch {
            val animal = withContext(Dispatchers.IO) {
                animalsRepository.get(animalId)
            }
            _state.value = _state.value.copy(
                animal = animal
            )
        }
    }
}