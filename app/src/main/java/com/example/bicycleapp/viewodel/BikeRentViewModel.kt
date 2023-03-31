package com.example.bicycleapp.viewodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bicycleapp.model.Bike
import com.example.bicycleapp.model.BikeBasketModel
import com.example.bicycleapp.repository.BikeRepository

class BikeRentViewModel : ViewModel() {

    private var repository: BikeRepository = BikeRepository()

    fun getBikeData(): MutableLiveData<ArrayList<Bike>> {
        return repository.getFirebaseBikeList()
    }

    fun getTotalFee() :MutableLiveData<Int>{
        return repository.getTotalFee()
    }

    fun addItemBasket(basketModel: BikeBasketModel) {
        repository.addItemBasket(basketModel)
    }
}