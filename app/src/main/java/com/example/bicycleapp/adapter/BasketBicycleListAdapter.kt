package com.example.bicycleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bicycleapp.databinding.BasketItemBinding
import com.example.bicycleapp.model.Bike

class BasketBicycleListAdapter(private val bikeList: ArrayList<Bike>):
    RecyclerView.Adapter<BasketBicycleListAdapter.ViewHolder>() {

    class ViewHolder(val binding: BasketItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(BasketItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val bike = bikeList.get(position)
        with(viewHolder) {
            binding.basketBikeName.text = bike.bikeName
            binding.basketBikePrice.text = bike.bikePrice
            binding.basketBikeImage.setImageResource(bike.bikeImage)
        }

    }

    override fun getItemCount() = bikeList.size
}