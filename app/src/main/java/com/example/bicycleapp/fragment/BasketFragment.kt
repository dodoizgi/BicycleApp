package com.example.bicycleapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bicycleapp.R
import com.example.bicycleapp.adapter.BasketBicycleListAdapter
import com.example.bicycleapp.data.SharedPreference
import com.example.bicycleapp.databinding.FragmentBasketBinding
import com.example.bicycleapp.model.Bike

class BasketFragment : Fragment() {

    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBasketBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreference = SharedPreference(view.context)
        val recyclerView = binding.basketRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        val adapter =
            BasketBicycleListAdapter(sharedPreference.getBasket("ORDER"))

        recyclerView.adapter= adapter

        binding.closeImage.setOnClickListener {
            findNavController().navigate(R.id.action_BasketFragment_to_BicycleRentalFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}