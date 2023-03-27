package com.example.bicycleapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.bicycleapp.model.Bike
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class BikeRepository {

    private var database: DatabaseReference = Firebase.database.reference
    private val bikeArray : ArrayList<Bike> = ArrayList()

     fun getFirebaseBikeList(): MutableLiveData<ArrayList<Bike>> {
        val mutableLiveData = MutableLiveData<ArrayList<Bike>>()
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val gson = Gson()
                    val json: String = postSnapshot.value.toString()
                    val type: Type = object : TypeToken<Bike>() {}.type
                    val bike : Bike = gson.fromJson(json, type)
                    bikeArray.add(bike)
                }
                mutableLiveData.value = bikeArray
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
        return mutableLiveData
    }
}