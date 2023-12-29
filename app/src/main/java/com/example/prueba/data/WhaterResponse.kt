package com.example.prueba.data

sealed class WhaterResponse <T>{

    data class Successful<T>(val content: T, ) : WhaterResponse<T>()
    data class Failed<T>(val errorMessage: String) :
        WhaterResponse<T>()

}