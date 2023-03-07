package com.example.detectorsismos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class MainViewModel: ViewModel() {

    private val mainRepository = MainRepository()

    private var _eqList: MutableLiveData<MutableList<Earthquake>> =
        MutableLiveData<MutableList<Earthquake>>()
    val eqList: LiveData<MutableList<Earthquake>>
        get() = _eqList

    init {
        viewModelScope.launch {

            _eqList.value = mainRepository.fetchEarthquakes()
        }
    }


}


