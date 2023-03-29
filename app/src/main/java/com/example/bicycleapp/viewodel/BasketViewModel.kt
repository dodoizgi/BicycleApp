package com.example.bicycleapp.viewodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bicycleapp.model.BikeBasketModel
import com.example.bicycleapp.repository.BasketRepository

class BasketViewModel : ViewModel() {

    private var basketRepository : BasketRepository = BasketRepository()

    fun getBasketData() :MutableLiveData<ArrayList<BikeBasketModel>> {
        return basketRepository.getFirebaseBasketList()
    }

    fun deleteBasket() :MutableLiveData<ArrayList<BikeBasketModel>>{
        basketRepository.deleteBasket()
        return getBasketData()
    }

    fun getTotalFee() :MutableLiveData<Int>{
        return basketRepository.getTotalFee()
    }

    fun addItemBasket(basketModel: BikeBasketModel) :MutableLiveData<ArrayList<BikeBasketModel>>{
        basketRepository.addItemBasket(basketModel)
        return getBasketData()
    }

    fun removeItemBasket(basketModel: BikeBasketModel) :MutableLiveData<ArrayList<BikeBasketModel>>{
        basketRepository.removeItembasket(basketModel)
        return getBasketData()
    }
}