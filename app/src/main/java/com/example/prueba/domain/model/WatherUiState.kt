package com.example.prueba.domain.model

sealed class WatherUiState {

    class Success(val data: WatherModel) : WatherUiState()
    class Fail(val errorMessage: String) : WatherUiState()

}