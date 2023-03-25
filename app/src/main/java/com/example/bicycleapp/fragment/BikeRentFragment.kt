package com.example.bicycleapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bicycleapp.Interface.BasketUpdate
import com.example.bicycleapp.R
import com.example.bicycleapp.adapter.BikeListAdapter
import com.example.bicycleapp.data.SharedPreference
import com.example.bicycleapp.databinding.FragmentBicycleRentalBinding
import com.example.bicycleapp.model.Bike

class BikeRentFragment : Fragment() , BasketUpdate {

    private var _binding: FragmentBicycleRentalBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreference: SharedPreference
    private lateinit var adapter : BikeListAdapter
    private lateinit var recyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBicycleRentalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event
        }
        callback.isEnabled = true

        recyclerView = binding.bikeRecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        adapter = BikeListAdapter(createActressList(),this)
        recyclerView.adapter= adapter
        sharedPreference = SharedPreference(view.context)

        binding.basketFee.text = sharedPreference.getTotalFee("ORDER")

        binding.basketLayout.setOnClickListener {
            sharedPreference.getBasket("ORDER")
            Navigation.findNavController(view).navigate(R.id.action_BicycleRentalFragment_to_BasketFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createActressList(): ArrayList<Bike> {
        return arrayListOf(
            Bike(1,
                "Bianchi",
                R.drawable.bicycle_image,
                15
            ),
            Bike(2,
                "Ümit",
                R.drawable.bike_1,
                20
            ),
            Bike(3,
                "Serraro ",
                R.drawable.bike_2,
                29
            ),
            Bike(4,
                "Cannondale",
                R.drawable.bike_3,
                19
            ),
            Bike(5,
                "Tec",
                R.drawable.bike_4,
                20
            ),
            Bike(6,
                "Belderia",
                R.drawable.bike_5,
                10
            ),
        )
    }


    private fun showBasket() {
        binding.basketLayout.visibility = View.VISIBLE
    }

    override fun update() {
        if (binding.basketLayout.visibility == View.GONE)
            showBasket()

        binding.basketFee.text = buildString {
        append(sharedPreference.getTotalFee("ORDER"))
        append("₺")
        }
    }
}