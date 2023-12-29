package com.example.prueba.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.prueba.domain.model.WatherUiState
import com.example.prueba.databinding.ActivityMainBinding
import com.example.prueba.di.WatherModule

class MainActivity : AppCompatActivity() {

    private  lateinit var binding : ActivityMainBinding
    private val watherViewModel : WatherViewModel by viewModels{
        WatherModule.provideWatheViewModelFactory()
    }

    private val watherViewObserver = Observer<WatherUiState> { uiState ->
        when(uiState){
            is WatherUiState.Success -> {
                binding.lblInitial.text = "Resultado exitoso ${uiState.data.id}!"
            }
            is WatherUiState.Fail -> {
                binding.lblInitial.text = "Fallo la aplicacion: ${uiState.errorMessage}"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        watherViewModel.uiState.observe(this, watherViewObserver)

        watherViewModel.getWather()
    }
}