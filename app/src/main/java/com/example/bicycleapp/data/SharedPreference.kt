package com.example.bicycleapp.data

import android.content.Context
import android.content.SharedPreferences
import com.example.bicycleapp.model.Bike
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SharedPreference(context : Context) {

    private val PREFS_NAME = "BikeApp"
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    var bikeArray : ArrayList<Bike> = ArrayList()


    fun addBasket(KEY_NAME: String, bike: Bike) {
        bikeArray = getBasket("ORDER")
        bikeArray.add(bike)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        val gson = Gson()
        val json: String = gson.toJson(bikeArray)
        editor.putString(KEY_NAME, json)
        editor.apply()
    }

    fun getBasket(key: String): ArrayList<Bike> {
        val gson = Gson()
        val json: String? = sharedPref.getString(key, null)
        val type: Type = object : TypeToken<ArrayList<Bike>>() {}.type
        val bikes : ArrayList<Bike> = gson.fromJson(json, type)

        return bikes
    }

   fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }
}