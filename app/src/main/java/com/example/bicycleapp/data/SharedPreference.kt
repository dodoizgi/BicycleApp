package com.example.bicycleapp.data

import android.content.Context
import android.content.SharedPreferences
import com.example.bicycleapp.model.BikeBasketModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SharedPreference(context : Context) {

    private val prefsName = "BikeApp"
    private val sharedPref: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
    private var bikeBasketArray : ArrayList<BikeBasketModel> = ArrayList()

    fun getBasket(key: String): ArrayList<BikeBasketModel>? {
        val gson = Gson()
        val json: String? = sharedPref.getString(key, null)
        val type: Type = object : TypeToken<ArrayList<BikeBasketModel>>() {}.type
        return gson.fromJson(json, type)
    }

    fun addItemBasket(KEY_NAME: String, bikeBasket: BikeBasketModel) {
        if (getBasket("ORDER")?.isEmpty() == false) { bikeBasketArray = getBasket("ORDER")!! }
        checkAndAdd(bikeBasket)
        if (bikeBasketArray.isEmpty())
            return

        val editor: SharedPreferences.Editor = sharedPref.edit()
        val gson = Gson()
        val json: String = gson.toJson(bikeBasketArray)
        editor.putString(KEY_NAME, json)
        editor.apply()
    }

    private fun checkAndAdd(bikeBasket: BikeBasketModel) {
        bikeBasketArray.forEach {
            if (bikeBasket.bikeId == it.bikeId) {
                it.bikeCount ++
                return
            }
        }
        bikeBasketArray.add(bikeBasket)
    }

    fun removeItembasket(KEY_NAME: String, bikeBasket: BikeBasketModel) {
        if (getBasket("ORDER")?.isEmpty() == false) { bikeBasketArray = getBasket("ORDER")!! }
        checkAndRemove(bikeBasket)
        if (bikeBasketArray.isEmpty())
            return

        val editor: SharedPreferences.Editor = sharedPref.edit()
        val gson = Gson()
        val json: String = gson.toJson(bikeBasketArray)
        editor.putString(KEY_NAME, json)
        editor.apply()
    }

    private fun checkAndRemove(bikeBasket: BikeBasketModel) {
        bikeBasketArray.forEach {
            if (bikeBasket.bikeId == it.bikeId) {
                if (it.bikeCount == 1){
                    bikeBasketArray.remove(it)
                    return
                }

                it.bikeCount --
                return
            }
        }
        bikeBasketArray.add(bikeBasket)
    }

    fun clearBasket() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove("ORDER")
        editor.apply()
    }
}