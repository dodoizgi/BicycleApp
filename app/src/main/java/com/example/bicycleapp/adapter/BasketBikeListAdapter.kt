package com.example.bicycleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bicycleapp.databinding.BasketItemBinding
import com.example.bicycleapp.model.BikeBasketModel
import com.example.bicycleapp.modelInterface.BasketDecrease
import com.example.bicycleapp.modelInterface.BasketIncrease

class BasketBikeListAdapter(private val bikeBasketList: ArrayList<BikeBasketModel>?, private val basketIncrease: BasketIncrease, private val basketDecrease: BasketDecrease):
    RecyclerView.Adapter<BasketBikeListAdapter.ViewHolder>() {

    class ViewHolder(val binding: BasketItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(BasketItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val bikeBasket = bikeBasketList?.get(position)
        with(viewHolder.binding) {
            basketBikeName.text = bikeBasket?.bikeName ?: ""
            basketBikePrice.text = bikeBasket?.bikePrice.toString()
            basketBikeImage.setImageResource(bikeBasket?.bikeImage ?: 0)
            basketPieceText.text = bikeBasket?.bikeCount.toString()

            basketIncreaseButton.setOnClickListener {
                if (bikeBasket != null) {
                    basketIncrease.increase(bikeBasket)
                }
            }
            basketDecreaseButton.setOnClickListener {
                if (bikeBasket != null) {
                    basketDecrease.decrease(bikeBasket)
                }
            }
        }
    }

    override fun getItemCount() = bikeBasketList?.size ?: 0
}