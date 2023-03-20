package com.example.bicycleapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bicycleapp.R
import com.example.bicycleapp.adapter.BasketBicycleListAdapter
import com.example.bicycleapp.data.SharedPreference
import com.example.bicycleapp.databinding.FragmentBasketBinding

class BasketFragment : Fragment() {

    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!
    private var sharedPreference : SharedPreference? = null
    private lateinit var adapter : BasketBicycleListAdapter
    private lateinit var recyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBasketBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreference = SharedPreference(view.context)
        recyclerView = binding.basketRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter =
            BasketBicycleListAdapter(sharedPreference!!.getBasket("ORDER"))

        recyclerView.adapter= adapter

        binding.closeImage.setOnClickListener {
            findNavController().navigate(R.id.action_BasketFragment_to_BicycleRentalFragment)
        }

        binding.deleteButton.setOnClickListener { deleteBasket() }
        binding.basketRentButton.setOnClickListener { dialogYesOrNo() }
    }

    private fun dialogYesOrNo() {
        val builder = AlertDialog.Builder(activity)
        builder.setPositiveButton("Yes") { dialog, id ->
            dialog.dismiss()
            deleteBasket()
        }
        builder.setNegativeButton("No", null)
        val alert = builder.create()
        alert.setMessage("Are you sure you want to rent the  bikes?")
        alert.show()
    }

    private fun deleteBasket() {
        sharedPreference?.clearBasket()
        adapter =
            BasketBicycleListAdapter(sharedPreference?.getBasket("ORDER"))
        recyclerView.adapter= adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}