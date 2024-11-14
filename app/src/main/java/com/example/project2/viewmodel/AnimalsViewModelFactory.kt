package com.example.project2.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.project2.db.AnimalsDao

class AnimalsViewModelFactory(
    private val application: Application,
    private val animalsDao: AnimalsDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimalsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnimalsViewModel(application, animalsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
