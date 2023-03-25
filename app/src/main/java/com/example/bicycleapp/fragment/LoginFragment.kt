package com.example.bicycleapp.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.bicycleapp.R
import com.example.bicycleapp.R.drawable.switch_trcks
import com.example.bicycleapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel
    private lateinit var dialog : AlertDialog
    private var user : FirebaseUser? = null


    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
          //Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_BicycleRentalFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog.dismiss()

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        dialog = "Loading..".setProgressDialog(requireContext())
        viewModel = LoginViewModel(requireActivity().application)
        viewModel.getUserData()!!.observe(requireActivity()) { firebaseUser ->
            if (firebaseUser != null) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_BicycleRentalFragment)
                onDestroy()
            }
        }

        with(binding) {
            signUp.setOnClickListener {
                signUp.background = resources.getDrawable(switch_trcks,null)
                signUp.setTextColor(resources.getColor(R.color.text,null))
                logIn.background = null
                singUpLayout.visibility = View.VISIBLE
                logInLayout.visibility = View.GONE
                logIn.setTextColor(resources.getColor(R.color.gray_700,null))
            }
            logIn.setOnClickListener {
                signUp.background = null
                signUp.setTextColor(resources.getColor(R.color.gray_700,null))
                logIn.background = resources.getDrawable(switch_trcks,null)
                singUpLayout.visibility = View.GONE
                logInLayout.visibility = View.VISIBLE
                logIn.setTextColor(resources.getColor(R.color.text,null))
            }

            logInButton.setOnClickListener {
                dialog.show()
                viewModel.signIn(binding.eMail.text.toString(), binding.passwords.text.toString())
            }

            RegisterButton.setOnClickListener{
                dialog.show()
                viewModel.register(binding.eMails.text.toString(), binding.passwordss.text.toString())
            }
        }
    }

    private fun String.setProgressDialog(context: Context): AlertDialog {
        val llPadding = 30
        val ll = LinearLayout(context)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam

        val progressBar = ProgressBar(context)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        progressBar.layoutParams = llParam

        llParam = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(context)
        tvText.text = this
        tvText.setTextColor(Color.parseColor("#000000"))
        tvText.textSize = 20.toFloat()
        tvText.layoutParams = llParam

        ll.addView(progressBar)
        ll.addView(tvText)

        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setView(ll)

        val dialog = builder.create()
        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window?.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window?.attributes = layoutParams
        }
        return dialog
    }
}