package com.example.prueba.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prueba.domain.usecase.GetCurrentWatherUseCase
import com.example.prueba.presentation.WatherViewModel

class WatherViewModelFactory(private val getWatherUseCase: GetCurrentWatherUseCase) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(WatherViewModel::class.java) -> return WatherViewModel(getWatherUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}