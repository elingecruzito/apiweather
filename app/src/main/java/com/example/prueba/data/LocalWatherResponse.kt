package com.example.prueba.data

sealed class LocalWatherResponse<T>{
    data class Successful<T>(val content: T, ) : LocalWatherResponse<T>()
    data class Failed<T>(val errorMessage: String) :
        LocalWatherResponse<T>()
}
