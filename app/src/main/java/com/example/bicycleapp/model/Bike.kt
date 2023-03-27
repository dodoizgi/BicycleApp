package com.example.bicycleapp.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Bike(var id: Int = 0,
                var bikeName: String?,
                var bikeImage : Int = 0,
                var bikePrice : Int = 0)
