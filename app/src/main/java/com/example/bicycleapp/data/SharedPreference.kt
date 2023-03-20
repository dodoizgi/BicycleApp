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
        if (getBasket("ORDER")?.isEmpty() == false) {
            bikeArray = getBasket("ORDER")!!
        }

        bikeArray.add(bike)

        if (bikeArray.isEmpty())
            return

        val editor: SharedPreferences.Editor = sharedPref.edit()
        val gson = Gson()
        val json: String = gson.toJson(bikeArray)
        editor.putString(KEY_NAME, json)
        editor.apply()
    }

    fun getBasket(key: String): ArrayList<Bike>? {
        val gson = Gson()
        val json: String? = sharedPref.getString(key, null)
        val type: Type = object : TypeToken<ArrayList<Bike>>() {}.type
        val bikes : ArrayList<Bike>? = gson.fromJson(json, type)

        return bikes
    }

    fun clearBasket() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove("ORDER")
        editor.apply()
    }
}