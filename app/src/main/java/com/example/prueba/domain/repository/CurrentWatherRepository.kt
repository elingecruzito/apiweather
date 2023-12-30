package com.example.prueba.domain.repository

import android.content.Context
import com.example.prueba.domain.model.WatherEntity

interface CurrentWatherRepository {
    fun insertCurrentWather(context: Context, watherEntity: WatherEntity)

}