package com.example.prueba.di

import com.example.prueba.data.CurrentWatherRepositoryDatabaseImp
import com.example.prueba.domain.usecase.GetCurrentWatherUseCase
import com.example.prueba.domain.repository.WhaterRepository
import com.example.prueba.data.WatherRespositoryImp
import com.example.prueba.domain.repository.CurrentWatherRepository
import com.example.prueba.domain.usecase.GetLocalCurrentWatherUseCase
import com.example.prueba.domain.usecase.InsertCurrentWatherUseCase

object WatherModule {

    fun provideWatheViewModelFactory() = WatherViewModelFactory(
        providerGetWatherUseCase(),
        providerInsertCurrentWather(),
        providerGetLocalAllCurrentWather()
    )

    private fun providerGetWatherUseCase() = GetCurrentWatherUseCase(
        providerWatherRepository()
    )

    private fun providerWatherRepository() : WhaterRepository {
        return WatherRespositoryImp
    }

    private fun providerInsertCurrentWather() = InsertCurrentWatherUseCase(
        providerCurrentRepository()
    )

    private fun providerGetLocalAllCurrentWather() = GetLocalCurrentWatherUseCase(
        providerCurrentRepository()
    )

    private fun providerCurrentRepository() : CurrentWatherRepository {
        return CurrentWatherRepositoryDatabaseImp()
    }




}