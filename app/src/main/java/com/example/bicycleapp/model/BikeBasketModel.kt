package com.example.bicycleapp.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class BikeBasketModel(var bikeId: Int = 0,
                           var bikeName: String?,
                           var bikeCount: Int = 1,
                           var bikePrice: Int=0,
                           var bikeImage: Int?)