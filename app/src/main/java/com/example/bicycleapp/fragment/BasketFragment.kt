package com.example.bicycleapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bicycleapp.R
import com.example.bicycleapp.adapter.BasketBikeListAdapter
import com.example.bicycleapp.databinding.FragmentBasketBinding
import com.example.bicycleapp.model.BikeBasketModel
import com.example.bicycleapp.modelInterface.BasketUpdate
import com.example.bicycleapp.viewodel.BasketViewModel

class BasketFragment : Fragment() ,BasketUpdate {

    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : BasketBikeListAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var viewModel: BasketViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentBasketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {}
        callback.isEnabled = true

        recyclerView = binding.basketRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        getData()

        binding.closeImage.setOnClickListener {
            findNavController().navigate(R.id.action_BasketFragment_to_BicycleRentalFragment)
        }

        binding.deleteButton.setOnClickListener { deleteBasket() }
        binding.basketRentButton.setOnClickListener { dialogYesOrNo() }
    }

    private fun getData() {
        viewModel = BasketViewModel()
        viewModel.getBasketData().observe(requireActivity()) {
            adapter = BasketBikeListAdapter(it, this)
            recyclerView.adapter = adapter
        }
        viewModel.getTotalFee().observe(requireActivity()) {
            binding.basketTotalFee.text = it.toString() }
    }

    private fun dialogYesOrNo() {
        val builder = AlertDialog.Builder(activity)
        builder.setPositiveButton("Yes") { dialog, _ ->
            dialog.dismiss()
            deleteBasket()
        }
        builder.setNegativeButton("No", null)
        val alert = builder.create()
        alert.setMessage("Are you sure you want to rent the  bikes?")
        alert.show()
    }

    private fun deleteBasket() {
        viewModel.deleteBasket().observe(requireActivity()) {
            getData()
        }
        binding.basketTotalFee.text = "0"
    }

    override fun increase(bikeBasketModel : BikeBasketModel) {
        viewModel.addItemBasket(bikeBasketModel).observe(requireActivity()) { getData() }
        viewModel.getTotalFee().observe(requireActivity()) {
            binding.basketTotalFee.text = it.toString() }
    }

    override fun decrease(bikeBasketModel: BikeBasketModel) {
        viewModel.removeItemBasket(bikeBasketModel).observe(requireActivity()) { getData() }
        viewModel.getTotalFee().observe(requireActivity()) {
            binding.basketTotalFee.text = it.toString() }
    }
}