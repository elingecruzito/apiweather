package com.example.prueba.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.prueba.R
import com.example.prueba.domain.model.WatherUiState
import com.example.prueba.databinding.ActivityMainBinding
import com.example.prueba.di.WatherModule
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private  lateinit var binding : ActivityMainBinding
    private val watherViewModel : WatherViewModel by viewModels{
        WatherModule.provideWatheViewModelFactory()
    }

    private lateinit var mMap: GoogleMap
    private var placesClient: PlacesClient ? = null

    private val watherViewObserver = Observer<WatherUiState> { uiState ->
        when(uiState){
            is WatherUiState.Success -> {
            }
            is WatherUiState.Fail -> {
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }
    fun init(){
        watherViewModel.uiState.observe(this, watherViewObserver)
        //watherViewModel.getWather()

        initPlacesClinet()
        createMapFragment()
        placesClientSelected()
    }
    fun initPlacesClinet(){
        if (!Places.isInitialized()){
            Places.initialize(this, getString(R.string.google_maps_key))
        }
        placesClient = Places.createClient(this)
    }
    fun createMapFragment(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    fun placesClientSelected(){
        val autoCompleteFragment = supportFragmentManager.findFragmentById(R.id.places) as AutocompleteSupportFragment ?
        autoCompleteFragment!!.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
            )
        )
        autoCompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener{
            override fun onError(status: Status) {
                Toast.makeText(applicationContext, status.statusMessage, Toast.LENGTH_LONG).show()
                Log.e("-----------------Places-----------------", status.toString())
            }

            override fun onPlaceSelected(place: Place) {
                //val address = place.address.toString()
                //val latlong = "${place.latLng.latitude}::${place.latLng.longitude}"

                mMap.clear()
                mMap.addMarker(MarkerOptions().position(place.latLng).title(place.address))
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(place.latLng, 18f),
                    1000,
                    null
                )
            }

        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }
}