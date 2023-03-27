package com.example.bicycleapp.viewodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bicycleapp.model.Bike
import com.example.bicycleapp.repository.BikeRepository

class BasketViewModel : ViewModel() {

    private var repository: BikeRepository = BikeRepository()

    fun getBikeData(): MutableLiveData<ArrayList<Bike>> {
        return repository.getFirebaseBikeList()
    }
}