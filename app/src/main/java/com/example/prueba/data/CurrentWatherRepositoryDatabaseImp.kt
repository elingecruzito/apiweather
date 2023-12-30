package com.example.prueba.data

import android.content.Context
import androidx.room.Room
import com.example.prueba.domain.model.WatherEntity
import com.example.prueba.domain.repository.CurrentWatherRepository

class CurrentWatherRepositoryDatabaseImp : CurrentWatherRepository {

    private var db : WatherDatabase? = null
    private val NAME_DATABASE = "pruebaCurrentWather"

    private fun getDataBase(context: Context){
        if(db == null || !db!!.isOpen) {
            db = Room.databaseBuilder(
                context,
                WatherDatabase::class.java,
                NAME_DATABASE
            )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    override fun insertCurrentWather(context: Context, watherEntity: WatherEntity) {
        getDataBase(context)
        db!!.watherDao().insertCurrentWather(watherEntity)
        db!!.close()
    }

    override fun getAllCurrentWather(context: Context) : LocalWatherResponse<List<WatherEntity>> {
        getDataBase(context)
        val listCurrentWather : List<WatherEntity> = db!!.watherDao().getAllCurrentWather()
        db!!.close()

        return if(listCurrentWather.isEmpty()){
            LocalWatherResponse.Failed("Lista Vacia!")
        }else{
            LocalWatherResponse.Successful(listCurrentWather)
        }
    }

}