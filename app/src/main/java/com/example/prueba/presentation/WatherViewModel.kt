package com.example.prueba.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prueba.domain.model.WatherUiState
import com.example.prueba.domain.usecase.GetWatherResult
import com.example.prueba.domain.usecase.GetWatherUseCase
import kotlinx.coroutines.launch

class WatherViewModel(private val getWatherUseCase: GetWatherUseCase) : ViewModel() {

    private val mutableUiState : MutableLiveData<WatherUiState> = MutableLiveData()
    val uiState : LiveData<WatherUiState> get() = mutableUiState


    fun getWather(){
        viewModelScope.launch {
            when(val result = getWatherUseCase.execute()){
                is GetWatherResult.Success -> mutableUiState.postValue(WatherUiState.Success(result.data))
                is GetWatherResult.Fail  -> mutableUiState.postValue(WatherUiState.Fail(result.messageError))
            }
        }
    }
}