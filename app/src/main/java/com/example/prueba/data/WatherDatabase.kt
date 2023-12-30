package com.example.prueba.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.prueba.data.WatherDao
import com.example.prueba.domain.model.WatherEntity

@Database(entities = arrayOf(WatherEntity::class), version = 2)
abstract class WatherDatabase : RoomDatabase(){
    abstract fun watherDao() : WatherDao
}