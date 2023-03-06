package com.example.detectorsismos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class MainViewModel: ViewModel() {

    private var _eqList: MutableLiveData<MutableList<Earthquake>> =
        MutableLiveData<MutableList<Earthquake>>()
    val eqList: LiveData<MutableList<Earthquake>>
        get() = _eqList

    init {
        viewModelScope.launch {

            _eqList.value = fetchEarthquakes()
        }
    }

    private suspend fun fetchEarthquakes(): MutableList<Earthquake> {
        return withContext(Dispatchers.IO) {
            val eqJsonResponse = service.getLastHourEarthquake()
            val eqList = parceEqResult(eqJsonResponse)

            eqList
        }
    }

    private fun parceEqResult(eqJsonResponse: EqJsonResponse): MutableList<Earthquake> {
        val eqList = mutableListOf<Earthquake>()
        val featureList = eqJsonResponse.features

        for(features in featureList){
            val properties = features.properties
            val id = features.id

            val mag = properties.mag
            val place = properties.place
            val time = properties.time

            val geometry = features.geometry
            val latitude = geometry.latitude
            val longitude = geometry.longitude

            eqList.add(Earthquake(id,place,mag,time,longitude,latitude))

        }

        return eqList
    }
}


