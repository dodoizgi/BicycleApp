package com.example.bicycleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bicycleapp.Interface.BasketUpdate
import com.example.bicycleapp.data.SharedPreference
import com.example.bicycleapp.databinding.BasketItemBinding
import com.example.bicycleapp.model.BikeBasketModel

class BasketBicycleListAdapter(private val bikeBasketList: ArrayList<BikeBasketModel>? , private val basketUpdate: BasketUpdate):
    RecyclerView.Adapter<BasketBicycleListAdapter.ViewHolder>() {

    class ViewHolder(val binding: BasketItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(BasketItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val bikeBasket = bikeBasketList?.get(position)
        with(viewHolder.binding) {
            basketBikeName.text = bikeBasket?.bikeName ?: ""
            basketBikePrice.text = bikeBasket?.bikePrice ?: ""
            basketBikeImage.setImageResource(bikeBasket?.bikeImage ?: 0)
            basketPieceText.text = bikeBasket?.bikeCount.toString()

            basketIncreaseButton.setOnClickListener {
                val sharedPreference = SharedPreference(it.context)
                if (bikeBasket != null) {sharedPreference.addItemBasket("ORDER",bikeBasket) }
                basketUpdate.update()
            }
            basketDecreaseButton.setOnClickListener {
                val sharedPreference = SharedPreference(it.context)
                if (bikeBasket != null) {sharedPreference.removeItembasket("ORDER",bikeBasket) }
                basketUpdate.update()
            }
        }
    }

    override fun getItemCount() = bikeBasketList?.size ?: 0
}