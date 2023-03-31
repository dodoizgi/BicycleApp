package com.example.bicycleapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.bicycleapp.model.Bike
import com.example.bicycleapp.model.BikeBasketModel
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
    private var bikeArray : ArrayList<Bike> = ArrayList()
    private var bikeBasketArray : ArrayList<BikeBasketModel> = ArrayList()
    private val mutableLiveDataInt : MutableLiveData<Int> = MutableLiveData()

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

    fun addItemBasket(bikeBasket: BikeBasketModel) {
        bikeBasketArray = getBikeBasketModels()
        checkAndAdd(bikeBasket, bikeBasketArray)
        database.setValue(bikeBasketArray)
    }

    private fun getBikeBasketModels() : ArrayList<BikeBasketModel> {
        Firebase.database("https://bikeeapp-basket.firebaseio.com").reference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    bikeBasketArray = ArrayList()

                    for (postSnapshot in dataSnapshot.children) {
                        val gson = Gson()
                        val json: String = postSnapshot.value.toString()
                        val bikeBasketModel: BikeBasketModel =
                            gson.fromJson(json, BikeBasketModel::class.java)
                        checkAndAdd(bikeBasketModel, bikeBasketArray)
                    }
                    //getTotalFee(bikeBasketArray)
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
        return bikeBasketArray
    }

    private fun checkAndAdd(bikeBasket: BikeBasketModel, bikeArrayy: ArrayList<BikeBasketModel>) {
        bikeArrayy.forEach {
            if (bikeBasket.bikeId == it.bikeId) {
                it.bikeCount ++
                return
            }
        }
        bikeArrayy.add(bikeBasket)
    }

    fun getTotalFee() : MutableLiveData<Int> {
        var totalCount = 0
        mutableLiveDataInt.value = 0
        bikeBasketArray = getBikeBasketModels()
        bikeBasketArray.forEach { totalCount += it.bikePrice * it.bikeCount }
        mutableLiveDataInt.value = totalCount

        return mutableLiveDataInt
    }
}