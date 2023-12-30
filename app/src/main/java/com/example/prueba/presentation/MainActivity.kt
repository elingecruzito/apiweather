package com.example.prueba.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.prueba.R
import com.example.prueba.domain.model.WatherUiState
import com.example.prueba.databinding.ActivityMainBinding
import com.example.prueba.di.WatherModule
import com.example.prueba.domain.model.LocalListWatherUiState
import com.google.android.gms.common.api.Status
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
                uiState.data.run {
                    mMap.addMarker(
                        MarkerOptions()
                            .position(LatLng(coord.lat, coord.lon))
                            .title(address)
                    )
                    mMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(coord.lat, coord.lon),
                            18f),
                        1000,
                        null
                    )
                    binding.run {
                        txtTemperature.text = main.temp.toString()
                        txtSensasion.text = main.feelsLike.toString()
                        txtMaxTemp.text = main.tempMax.toString()
                        txtMinTemp.text = main.tempMin.toString()
                        txtPresion.text = main.pressure.toString()
                        txtHumedad.text = main.humidity.toString()
                        clDataWather.visibility = View.VISIBLE
                    }
                }
            }
            is WatherUiState.Fail -> {
                Toast.makeText(applicationContext, uiState.errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
    private val localWatherViewObserver = Observer<LocalListWatherUiState> { uiState ->
        when(uiState){
            is LocalListWatherUiState.Success -> {

                binding.placesAutocomplete.setAdapter(
                    ArrayAdapter(this, android.R.layout.select_dialog_item, uiState.data)
                )
                binding.placesAutocomplete.onItemClickListener = object : OnItemClickListener {
                    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, arg3: Long) {
                        val currentWather = watherViewModel.getItemSelected(parent!!.getItemAtPosition(position).toString())
                        mMap.clear()
                        mMap.addMarker(
                            MarkerOptions()
                                .position(LatLng(currentWather.lat!!, currentWather.lon!!))
                                .title(currentWather.address)
                        )
                        mMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(currentWather.lat!!, currentWather.lon!!),
                                18f),
                            1000,
                            null
                        )
                        binding.run {
                            txtTemperature.text = currentWather.temp.toString()
                            txtSensasion.text = currentWather.feelsLike.toString()
                            txtMaxTemp.text = currentWather.tempMax.toString()
                            txtMinTemp.text = currentWather.tempMin.toString()
                            txtPresion.text = currentWather.pressure.toString()
                            txtHumedad.text = currentWather.humidity.toString()
                            clDataWather.visibility = View.VISIBLE
                        }
                        hideKeyboard()
                    }
                }
            }
            is LocalListWatherUiState.Fail -> {
                Toast.makeText(applicationContext, uiState.errorMessage, Toast.LENGTH_LONG).show()
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
        watherViewModel.localUiState.observe(this, localWatherViewObserver)

        binding.clDataWather.visibility = View.GONE

        vibilitySearchComponents()
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
            }

            override fun onPlaceSelected(place: Place) {
                mMap.clear()
                binding.clDataWather.visibility = View.GONE
                watherViewModel.getCurrentWather(place.address.toString(), place.latLng, applicationContext)
            }

        })
    }

    private fun vibilitySearchComponents(){
        binding.run {
            if (checkConection()){
                ctPalcesGoogle.visibility = View.VISIBLE
                ctPlacesAutocomplete.visibility = View.GONE
            }else{
                ctPlacesAutocomplete.visibility = View.VISIBLE
                ctPalcesGoogle.visibility = View.GONE
                watherViewModel.getLocalCurrentWather(applicationContext)
            }
        }
    }
    private fun checkConection() : Boolean{
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    private fun hideKeyboard(){
        val view = this.currentFocus
        if(view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }
}