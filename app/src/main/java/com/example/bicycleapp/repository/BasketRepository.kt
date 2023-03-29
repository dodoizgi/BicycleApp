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
    private var bikeArray : ArrayList<BikeBasketModel> = ArrayList()

    fun getFirebaseBasketList(): MutableLiveData<ArrayList<BikeBasketModel>> {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                bikeArray = ArrayList()
                for (postSnapshot in dataSnapshot.children) {
                    val gson = Gson()
                    val json: String = postSnapshot.value.toString()
                    val bikeBasketModel : BikeBasketModel = gson.fromJson(json, BikeBasketModel::class.java)
                    checkAndAdd(bikeBasketModel,bikeArray)
                }
                mutableLiveData.value = bikeArray
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
        return mutableLiveData
    }

    fun getTotalFee() : MutableLiveData<Int> {
        var totalCount = 0
        val mutableLiveDataInt : MutableLiveData<Int> = MutableLiveData()

        bikeArray = getFirebaseBasketList().value ?: ArrayList()

        if (bikeArray.isEmpty())
            return mutableLiveDataInt

        bikeArray.forEach { totalCount += it.bikePrice * it.bikeCount }
        mutableLiveDataInt.value = totalCount
        return mutableLiveDataInt
    }

    fun addItemBasket(bikeBasket: BikeBasketModel) {
        bikeArray = getFirebaseBasketList().value!!
        checkAndAdd(bikeBasket, bikeArray)
        database.setValue(bikeArray)
    }

    fun removeItembasket(bikeBasket: BikeBasketModel) {
        bikeArray = getFirebaseBasketList().value!!
        checkAndRemove(bikeBasket,bikeArray)
        database.setValue(bikeArray)
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