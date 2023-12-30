package com.example.prueba.domain.repository

import com.example.prueba.data.WatherResponse
import com.example.prueba.domain.model.CurrentWather
import com.google.android.gms.maps.model.LatLng

interface WhaterRepository {
    suspend fun getCurrentWhater(adddress: String?, latLng: LatLng?) : WatherResponse<CurrentWather>
}