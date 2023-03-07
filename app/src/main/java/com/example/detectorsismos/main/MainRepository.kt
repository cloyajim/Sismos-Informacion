package com.example.detectorsismos.main

import com.example.detectorsismos.Earthquake
import com.example.detectorsismos.api.EqJsonResponse
import com.example.detectorsismos.api.service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository {
    suspend fun fetchEarthquakes(): MutableList<Earthquake> {
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