package com.example.bicycleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bicycleapp.modelInterface.BasketIncrease
import com.example.bicycleapp.databinding.BicycleItemBinding
import com.example.bicycleapp.model.Bike
import com.example.bicycleapp.model.BikeBasketModel

class BikeListAdapter(private val bikeList : ArrayList<Bike>, private val basketIncrease: BasketIncrease):
    RecyclerView.Adapter<BikeListAdapter.ViewHolder>() {

    private var count: Int = 1

    class ViewHolder(val binding: BicycleItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(BicycleItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val bike : Bike = bikeList.get(position)
        with(viewHolder) {
            binding.bicycleName.text = bike.bikeName
            binding.bicyclePrice.text = bike.bikePrice.toString()
            binding.bicycleImage.setImageResource(bike.bikeImage)
            binding.plusButton.setOnClickListener {
                val bikeBasketModel = BikeBasketModel(bike.id,bike.bikeName,count,bike.bikePrice,bike.bikeImage)
                basketIncrease.increase(bikeBasketModel)
            }
        }
    }
    override fun getItemCount() = bikeList.size
}