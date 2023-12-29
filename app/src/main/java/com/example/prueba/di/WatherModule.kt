package com.example.prueba.di

import com.example.prueba.domain.usecase.GetWatherUseCase
import com.example.prueba.domain.repository.WhaterRepository
import com.example.prueba.data.WatherRespositoryImp

object WatherModule {

    fun provideWatheViewModelFactory() = WatherViewModelFactory(
        providerGetWatherUseCase()
    )

    private fun providerGetWatherUseCase() = GetWatherUseCase(
        providerWatherRepository()
    )

    private fun providerWatherRepository() : WhaterRepository {
        return WatherRespositoryImp
    }

}