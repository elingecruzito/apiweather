package com.example.prueba.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prueba.domain.model.CoordModel
import com.example.prueba.domain.model.CurrentWather
import com.example.prueba.domain.model.CurrentWatherModel
import com.example.prueba.domain.model.LocalListWatherUiState
import com.example.prueba.domain.model.LocalWatherModel
import com.example.prueba.domain.model.MainModel
import com.example.prueba.domain.model.WatherEntity
import com.example.prueba.domain.model.WatherUiState
import com.example.prueba.domain.usecase.GetWatherResult
import com.example.prueba.domain.usecase.GetCurrentWatherUseCase
import com.example.prueba.domain.usecase.GetLocalCurrentWatherUseCase
import com.example.prueba.domain.usecase.GetLocalWatherResult
import com.example.prueba.domain.usecase.InsertCurrentWatherUseCase
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class WatherViewModel(
    private val getWatherUseCase: GetCurrentWatherUseCase,
    private val insertCurrentWatherUseCase: InsertCurrentWatherUseCase,
    private val getLocalCurrentWatherUseCase: GetLocalCurrentWatherUseCase
) : ViewModel() {

    private val mutableUiState : MutableLiveData<WatherUiState> = MutableLiveData()
    val uiState : LiveData<WatherUiState> get() = mutableUiState

    private val localMutableUiState : MutableLiveData<LocalListWatherUiState> = MutableLiveData()
    val localUiState : LiveData<LocalListWatherUiState> get() = localMutableUiState

    private var currentWather : CurrentWatherModel? = null
    private var listLocalCurrentWather : List<WatherEntity> ? = null

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

    fun getLocalCurrentWather(context: Context){
        viewModelScope.launch {
            when(val result = getLocalCurrentWatherUseCase.execute(context)){
                is GetLocalWatherResult.Success -> {
                    listLocalCurrentWather = result.data
                    var listLocalWather : ArrayList<String> = arrayListOf()
                    listLocalCurrentWather!!.forEach { item ->
                        item?.address?.let { listLocalWather.add(it) }
                    }
                    localMutableUiState.postValue(LocalListWatherUiState.Success(listLocalWather))
                }
                is GetLocalWatherResult.Fail -> {
                    localMutableUiState.postValue(LocalListWatherUiState.Fail(result.messageError))
                }
            }
        }
    }

    fun getItemSelected(itemSelected: String) : LocalWatherModel {
        var localCurrentWather : WatherEntity ? = null
        listLocalCurrentWather!!.forEach { item ->
            if(item.address!!.equals(itemSelected)){
                localCurrentWather = item
            }
        }
        return localCurrentWather!!.toModel()
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

    private fun WatherEntity.toModel() : LocalWatherModel{
        return LocalWatherModel(
            address = address,
            lon = lon,
            lat = lat,
            temp = temp,
            feelsLike = feelsLike,
            tempMin = tempMin,
            tempMax = tempMax,
            pressure = pressure,
            humidity = humidity
        )
    }
}