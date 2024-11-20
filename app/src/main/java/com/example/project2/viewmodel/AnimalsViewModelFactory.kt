package com.example.project2.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.project2.db.AnimalsDao

class AnimalsViewModelFactory(private val application: Application, val animalsDao: AnimalsDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AnimalsViewModel(application, animalsDao) as T
    }
}

