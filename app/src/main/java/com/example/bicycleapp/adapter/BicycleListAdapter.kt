package com.example.bicycleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bicycleapp.databinding.BicycleItemBinding
import com.example.bicycleapp.model.Bike

class BicycleListAdapter(private val bikeList : ArrayList<Bike>):
    RecyclerView.Adapter<BicycleListAdapter.ViewHolder>() {

    class ViewHolder(val binding: BicycleItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(BicycleItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.bicycleName.text = bikeList.get(position).bikeName
        viewHolder.binding.bicyclePrice.text = bikeList.get(position).bikePrice
        viewHolder.binding.bicycleImage.setImageResource(bikeList.get(position).bikeImage)
    }

    override fun getItemCount() = bikeList.size
}