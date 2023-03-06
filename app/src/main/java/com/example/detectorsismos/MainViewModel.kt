package com.example.detectorsismos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject

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
            val eqListString = service.getLastHourEarthquake()
            val eqList = parceEqResult(eqListString)

            eqList
        }
    }

    private fun parceEqResult(eqListString: String): MutableList<Earthquake> {
        val eqJsonObject = JSONObject(eqListString)
        val featuresJsonArray = eqJsonObject.getJSONArray("features")

        val eqList = mutableListOf<Earthquake>()

        for (i in 0 until featuresJsonArray.length()) {
            val featuresJSONObject = featuresJsonArray[i] as JSONObject
            val id = featuresJSONObject.getString("id")

            val propertiesJsonObject = featuresJSONObject.getJSONObject("properties")
            val magnitud = propertiesJsonObject.getDouble("mag")
            val place = propertiesJsonObject.getString("place")
            val time = propertiesJsonObject.getLong("time")

            val geometryJsonObject = featuresJSONObject.getJSONObject("geometry")
            val coordinateJsonArray = geometryJsonObject.getJSONArray("coordinates")
            val longitude = coordinateJsonArray.getDouble(0)
            val latitude = coordinateJsonArray.getDouble(1)

            val earthquake = Earthquake(id, place, magnitud, time, longitude, latitude)
            eqList.add(earthquake)
        }
        return eqList
    }
}


