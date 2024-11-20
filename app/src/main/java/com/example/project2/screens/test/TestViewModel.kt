package com.example.project2.screens.test

import androidx.lifecycle.ViewModel
import com.example.project2.App
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class TestViewModel(): ViewModel() {

    // https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state#8
    //or
    // https://metanit.com/kotlin/jetpack/16.3.php

    private val dao = App.animalsDao

    private val _state = MutableStateFlow(TestState())

    val state : StateFlow<TestState>
        get() = _state.asStateFlow()

    private var internalField = 0

    fun inc(){
        internalField++
        _state.value = state.value.copy(data = internalField.toString())
    }

    fun foo(){
    }

}