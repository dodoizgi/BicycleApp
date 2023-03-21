package com.example.bicycleapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bicycleapp.R
import com.example.bicycleapp.adapter.BicycleListAdapter
import com.example.bicycleapp.data.SharedPreference
import com.example.bicycleapp.databinding.FragmentBicycleRentalBinding
import com.example.bicycleapp.model.Bike


class BicycleRentalFragment : Fragment() {

    private var _binding: FragmentBicycleRentalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBicycleRentalBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.bikeRecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 3)

        val adapter =
            BicycleListAdapter(createActressList())

        recyclerView.adapter= adapter

        binding.basketButton.setOnClickListener {
            val sharedPreference = SharedPreference(view.context)
            sharedPreference.getBasket("ORDER")
            Navigation.findNavController(view).navigate(R.id.action_BicycleRentalFragment_to_BasketFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createActressList(): ArrayList<Bike> {
        return arrayListOf<Bike>(
            Bike(1,
                "Bianchi",
                R.drawable.bicycle_image,
                "15tl"
            ),
            Bike(2,
                "Ümit",
                R.drawable.bike_1,
                "20tl"
            ),
            Bike(3,
                "Serraro ",
                R.drawable.bike_2,
                "29tl"
            ),
            Bike(4,
                "Cannondale",
                R.drawable.bike_3,
                "19tl"
            ),
            Bike(5,
                "Tec",
                R.drawable.bike_4,
                "20tl"
            ),
            Bike(6,
                "Belderia",
                R.drawable.bike_5,
                "10tl"
            ),
            Bike(1,
                "Bianchi",
                R.drawable.bike_6,
                "18tl"
            ),
            Bike(2,
                "Ümit",
                R.drawable.bike_5,
                "20tl"
            ),
            Bike(3,
                "Serraro ",
                R.drawable.bike_2,
                "23tl"
            ),
            Bike(4,
                "Cannondale",
                R.drawable.bike_1,
                "22tl"
            ),
            Bike(5,
                "Tec",
                R.drawable.bicycle_image,
                "25tl"
            ),
            Bike(6,
                "Belderia",
                R.drawable.bike_3,
                "10tl"
            ),
        )
    }

}