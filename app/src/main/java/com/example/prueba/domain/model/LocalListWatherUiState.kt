package com.example.prueba.domain.model

sealed class LocalListWatherUiState{
    class Success(val data: List<String>) : LocalListWatherUiState()
    class Fail(val errorMessage: String) : LocalListWatherUiState()
}
