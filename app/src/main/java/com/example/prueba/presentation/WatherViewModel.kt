package com.example.prueba.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prueba.domain.model.CoordModel
import com.example.prueba.domain.model.CurrentWather
import com.example.prueba.domain.model.CurrentWatherModel
import com.example.prueba.domain.model.MainModel
import com.example.prueba.domain.model.WatherEntity
import com.example.prueba.domain.model.WatherUiState
import com.example.prueba.domain.usecase.GetWatherResult
import com.example.prueba.domain.usecase.GetCurrentWatherUseCase
import com.example.prueba.domain.usecase.InsertCurrentWatherUseCase
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class WatherViewModel(
    private val getWatherUseCase: GetCurrentWatherUseCase,
    private val insertCurrentWatherUseCase: InsertCurrentWatherUseCase
) : ViewModel() {

    private val mutableUiState : MutableLiveData<WatherUiState> = MutableLiveData()
    val uiState : LiveData<WatherUiState> get() = mutableUiState
    private var currentWather : CurrentWatherModel? = null

    fun getCurrentWather(adddress: String?, latLng: LatLng?, context: Context){
        viewModelScope.launch {
            when(val result = getWatherUseCase.execute(adddress, latLng)){
                is GetWatherResult.Success -> {

                    currentWather = result.data.toModel()
                    currentWather!!.address = adddress

                    insertCurrentWatherUseCase.execute(
                        context,
                        currentWather!!.toEntity()
                    )
                    mutableUiState.postValue(WatherUiState.Success(currentWather!!))
                }
                is GetWatherResult.Fail  -> mutableUiState.postValue(WatherUiState.Fail(result.messageError))
            }
        }
    }

    private fun CurrentWather.toModel() : CurrentWatherModel{
        return CurrentWatherModel(
            coord = CoordModel(
                lon = coord.lon,
                lat = coord.lat
            ),
            main = MainModel(
                temp = main.temp,
                feelsLike = main.feelsLike,
                tempMin = main.tempMin,
                tempMax = main.tempMax,
                pressure = main.pressure,
                humidity = main.humidity
            )
        )
    }

    private fun CurrentWatherModel.toEntity() : WatherEntity {
        return WatherEntity(
            address = address,
            lon = coord.lon,
            lat = coord.lat,
            temp = main.temp,
            feelsLike = main.feelsLike,
            tempMin = main.tempMin,
            tempMax = main.tempMax,
            pressure = main.pressure,
            humidity = main.humidity
        )
    }
}