package com.example.bicycleapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.bicycleapp.R
import com.example.bicycleapp.R.drawable.switch_trcks
import com.example.bicycleapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            singUp.setOnClickListener {
                singUp.background = resources.getDrawable(switch_trcks,null)
                singUp.setTextColor(resources.getColor(R.color.text,null))
                logIn.background = null
                singUpLayout.visibility = View.VISIBLE
                logInLayout.visibility = View.GONE
                logIn.setTextColor(resources.getColor(R.color.gray_700,null))
            }
            logIn.setOnClickListener {
                singUp.background = null
                singUp.setTextColor(resources.getColor(R.color.gray_700,null))
                logIn.background = resources.getDrawable(switch_trcks,null)
                singUpLayout.visibility = View.GONE
                logInLayout.visibility = View.VISIBLE
                logIn.setTextColor(resources.getColor(R.color.text,null))
            }

            signIn.setOnClickListener {

                if (eMail.text.toString().equals("a") && passwords.text.toString().equals("a")) {
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_BicycleRentalFragment)
                }
            }
        }

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        // TODO: Use the ViewModel
    }

}