package com.example.prueba.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prueba.domain.model.WatherUiState
import com.example.prueba.domain.usecase.GetWatherResult
import com.example.prueba.domain.usecase.GetCurrentWatherUseCase
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class WatherViewModel(private val getWatherUseCase: GetCurrentWatherUseCase) : ViewModel() {

    private val mutableUiState : MutableLiveData<WatherUiState> = MutableLiveData()
    val uiState : LiveData<WatherUiState> get() = mutableUiState


    fun getCurrentWather(adddress: String?, latLng: LatLng?){
        viewModelScope.launch {
            when(val result = getWatherUseCase.execute(adddress, latLng)){
                is GetWatherResult.Success -> mutableUiState.postValue(WatherUiState.Success(result.data))
                is GetWatherResult.Fail  -> mutableUiState.postValue(WatherUiState.Fail(result.messageError))
            }
        }
    }
}