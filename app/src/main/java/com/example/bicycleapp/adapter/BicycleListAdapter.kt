package com.example.bicycleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.bicycleapp.R
import com.example.bicycleapp.data.SharedPreference
import com.example.bicycleapp.databinding.BicycleItemBinding
import com.example.bicycleapp.model.Bike
import com.example.bicycleapp.model.BikeBasketModel

class BicycleListAdapter(private val bikeList : ArrayList<Bike>):
    RecyclerView.Adapter<BicycleListAdapter.ViewHolder>() {

    private var count: Int = 1
    //private lateinit var bikeBasketModel : BikeBasketModel

    class ViewHolder(val binding: BicycleItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(BicycleItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val bike : Bike = bikeList.get(position)
        with(viewHolder) {
            binding.bicycleName.text = bike.bikeName
            binding.bicyclePrice.text = bike.bikePrice
            binding.bicycleImage.setImageResource(bike.bikeImage)

            binding.plusButton.setOnClickListener {

                val sharedPreference:SharedPreference=SharedPreference(itemView.context)
                sharedPreference.addItemBasket("ORDER",BikeBasketModel(bike.id,bike.bikeName,count,bike.bikePrice,bike.bikeImage))
                Navigation.findNavController(itemView).navigate(R.id.action_BicycleRentalFragment_to_BasketFragment)
            }
        }
    }

    override fun getItemCount() = bikeList.size
}