package com.example.prueba.data

import com.example.prueba.domain.model.WatherModel
import com.example.prueba.domain.repository.WhaterRepository

object WatherRespositoryImp : WhaterRepository {
    override suspend fun getWhater(): WhaterResponse<WatherModel> {
        //return WhaterResponse.Successful(WatherModel(1L))
        return WhaterResponse.Failed("No se encontro ningun dato!")
    }
}