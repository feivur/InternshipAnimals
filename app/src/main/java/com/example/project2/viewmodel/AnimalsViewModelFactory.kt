package com.example.project2.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AnimalsViewModelFactory(private val application: Application) ://,animalsDao)
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AnimalsViewModel(application) as T//,animalsDao)
    }
}

