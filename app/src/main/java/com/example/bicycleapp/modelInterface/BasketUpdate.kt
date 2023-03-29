package com.example.bicycleapp.modelInterface

import com.example.bicycleapp.model.BikeBasketModel

interface BasketUpdate {
    fun increase(bikeBasketModel: BikeBasketModel)
    fun decrease(bikeBasketModel: BikeBasketModel)
}