package com.example.prueba.data

import com.example.prueba.domain.model.CurrentWather
import com.example.prueba.domain.repository.WhaterRepository
import com.example.prueba.network.ApiClient
import com.google.android.gms.maps.model.LatLng

object WatherRespositoryImp : WhaterRepository {

    private var API_KEY = "c8883f08f1a461ec7702419a780ec469"

    override suspend fun getCurrentWhater(
        adddress: String?,
        latLng: LatLng?
    ): WatherResponse<CurrentWather> {
        try {
            val call = ApiClient.apiService.getCurrentWather(latLng!!.latitude, latLng!!.longitude, API_KEY)
            return WatherResponse.Successful(call)
        }catch (ex : Exception){
            return WatherResponse.Failed(ex.message.toString())
        }

    }
}