package com.example.bicycleapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.bicycleapp.model.BikeBasketModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class BasketRepository {

    private var database: DatabaseReference = Firebase.database("https://bikeeapp-basket.firebaseio.com").reference
    private var mutableLiveData = MutableLiveData<ArrayList<BikeBasketModel>>()
    private var bikeBasketArray : ArrayList<BikeBasketModel> = ArrayList()
    private val mutableLiveDataInt : MutableLiveData<Int> = MutableLiveData()

    fun getFirebaseBasketList(): MutableLiveData<ArrayList<BikeBasketModel>> {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                bikeBasketArray = ArrayList()
                for (postSnapshot in dataSnapshot.children) {
                    val gson = Gson()
                    val json: String = postSnapshot.value.toString()
                    val bikeBasketModel : BikeBasketModel = gson.fromJson(json, BikeBasketModel::class.java)
                    checkAndAdd(bikeBasketModel,bikeBasketArray)
                }
                mutableLiveData.value = bikeBasketArray
                getTotalFee(bikeBasketArray)
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
        return mutableLiveData
    }

    fun getTotalFee(bikeBasketArray: ArrayList<BikeBasketModel>) : MutableLiveData<Int> {
        var totalCount = 0
        mutableLiveDataInt.value = 0
        bikeBasketArray.forEach { totalCount += it.bikePrice * it.bikeCount }
        mutableLiveDataInt.value = totalCount

        return mutableLiveDataInt
    }

    fun addItemBasket(bikeBasket: BikeBasketModel) {
        bikeBasketArray = getFirebaseBasketList().value ?: ArrayList()
        checkAndAdd(bikeBasket, bikeBasketArray)
        database.setValue(bikeBasketArray)
    }

    fun removeItembasket(bikeBasket: BikeBasketModel) {
        bikeBasketArray = getFirebaseBasketList().value ?: ArrayList()
        checkAndRemove(bikeBasket,bikeBasketArray)
        database.setValue(bikeBasketArray)
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

    private fun checkAndRemove(bikeBasket: BikeBasketModel, bikeArrayy: ArrayList<BikeBasketModel>) {
        bikeArrayy.forEach {
            if (bikeBasket.bikeId == it.bikeId) {
                if (it.bikeCount == 1){
                    bikeArrayy.remove(it)
                    return
                }

                it.bikeCount --
                return
            }
        }
        bikeArrayy.add(bikeBasket)
    }

    fun deleteBasket () {
        database.removeValue().addOnSuccessListener { }
    }
}