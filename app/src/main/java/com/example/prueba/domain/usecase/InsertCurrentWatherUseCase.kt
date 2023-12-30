package com.example.prueba.domain.usecase

import android.content.Context
import com.example.prueba.domain.model.WatherEntity
import com.example.prueba.domain.repository.CurrentWatherRepository

class InsertCurrentWatherUseCase(val repository: CurrentWatherRepository) {
    fun execute(context: Context, watherEntity: WatherEntity) {
       repository.insertCurrentWather(context, watherEntity)
    }
}