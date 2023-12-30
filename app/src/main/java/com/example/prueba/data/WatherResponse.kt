package com.example.prueba.data

sealed class WatherResponse <T>{
    data class Successful<T>(val content: T, ) : WatherResponse<T>()
    data class Failed<T>(val errorMessage: String) :
        WatherResponse<T>()

}