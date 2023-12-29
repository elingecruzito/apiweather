package com.example.prueba.domain.repository

import com.example.prueba.data.WhaterResponse
import com.example.prueba.domain.model.WatherModel

interface WhaterRepository {

    suspend fun getWhater() : WhaterResponse<WatherModel>

}