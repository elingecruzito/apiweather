package com.example.prueba.domain.usecase

import com.example.prueba.data.WhaterResponse
import com.example.prueba.domain.model.WatherModel
import com.example.prueba.domain.repository.WhaterRepository

class GetWatherUseCase(private val respository : WhaterRepository) {

    suspend fun execute() : GetWatherResult {
        return when(val result = respository.getWhater()){
            is WhaterResponse.Successful -> GetWatherResult.Success(result.content)
            is WhaterResponse.Failed -> GetWatherResult.Fail(result.errorMessage)
        }
    }

}

sealed class GetWatherResult{
    data class Success(val data : WatherModel) : GetWatherResult()
    data class Fail(val messageError : String) : GetWatherResult()

}