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
import com.example.bicycleapp.modelInterface.BasketIncrease
import com.example.bicycleapp.R
import com.example.bicycleapp.adapter.BikeListAdapter
import com.example.bicycleapp.data.SharedPreference
import com.example.bicycleapp.databinding.FragmentBicycleRentalBinding
import com.example.bicycleapp.model.BikeBasketModel
import com.example.bicycleapp.viewodel.BikeRentViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BikeRentFragment : Fragment() , BasketIncrease {

    private var _binding: FragmentBicycleRentalBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreference: SharedPreference
    private lateinit var adapter : BikeListAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var database: DatabaseReference
    private lateinit var viewModel: BikeRentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBicycleRentalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {}
        callback.isEnabled = true

        database = Firebase.database.reference
        sharedPreference = SharedPreference(view.context)
        recyclerView = binding.bikeRecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 3)

        getRentData()

        binding.basketLayout.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_BicycleRentalFragment_to_BasketFragment)
        }
    }

    private  fun getRentData() {
        viewModel = BikeRentViewModel()

        viewModel.getBikeData().observe(requireActivity()) {
            adapter = BikeListAdapter(it, this)
            recyclerView.adapter = adapter
        }
        viewModel.getTotalFee().observe(requireActivity()) {
            binding.basketFee.text = buildString {
                append(it)
                append("₺")
            }
        }
        if (sharedPreference.getTotalFee("ORDER") >= 1)
            showBasket()
    }

    private fun showBasket() {
        binding.basketLayout.visibility = View.VISIBLE
    }

    override fun increase(bikeBasketModel: BikeBasketModel) {
        if (binding.basketLayout.visibility == View.GONE)
            showBasket()

        viewModel.addItemBasket(bikeBasketModel)
        getRentData()
    }
}