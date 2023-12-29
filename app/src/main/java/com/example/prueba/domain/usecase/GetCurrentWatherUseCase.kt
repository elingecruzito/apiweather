package com.example.prueba.domain.usecase

import com.example.prueba.data.WatherResponse
import com.example.prueba.domain.model.CurrentWather
import com.example.prueba.domain.repository.WhaterRepository
import com.google.android.gms.maps.model.LatLng

class GetCurrentWatherUseCase(private val respository : WhaterRepository) {

    suspend fun execute(adddress: String?, latLng: LatLng?) : GetWatherResult {
        return when(val result = respository.getCurrentWhater(adddress, latLng)){
            is WatherResponse.Successful -> GetWatherResult.Success(result.content)
            is WatherResponse.Failed -> GetWatherResult.Fail(result.errorMessage)
        }
    }

}

sealed class GetWatherResult{
    data class Success(val data : CurrentWather) : GetWatherResult()
    data class Fail(val messageError : String) : GetWatherResult()

}