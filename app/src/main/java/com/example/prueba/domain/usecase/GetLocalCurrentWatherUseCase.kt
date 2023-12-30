package com.example.prueba.domain.usecase

import android.content.Context
import com.example.prueba.data.LocalWatherResponse
import com.example.prueba.domain.model.WatherEntity
import com.example.prueba.domain.repository.CurrentWatherRepository

class GetLocalCurrentWatherUseCase(val repository: CurrentWatherRepository) {
    fun execute(context: Context) : GetLocalWatherResult {
        return when(val result = repository.getAllCurrentWather(context)){
            is LocalWatherResponse.Successful -> GetLocalWatherResult.Success(result.content)
            is LocalWatherResponse.Failed -> GetLocalWatherResult.Fail(result.errorMessage)
        }
    }
}

sealed class GetLocalWatherResult{
    data class Success(val data : List<WatherEntity>) : GetLocalWatherResult()
    data class Fail(val messageError : String) : GetLocalWatherResult()

}

