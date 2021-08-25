package com.bakhus.androidtest.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bakhus.androidtest.databinding.RegisterFragmentBinding
import com.bakhus.androidtest.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels()
    private var _binding: RegisterFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val repeatedPassword = binding.etPasswordConfirm.text.toString()
            val name = binding.etName.text.toString()
            val surname = binding.etSurname.text.toString()
            val middlename = binding.etMiddleName.text.toString()
            viewModel.register(email, password, repeatedPassword, name, surname, middlename)
        }
    }

    private fun subscribeToObservers() {
        viewModel.registerStatus.observe(viewLifecycleOwner, Observer { result ->

            result?.let {
                when (result.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.isGone = true
                        Toast.makeText(
                            requireContext(),
                            result.data ?: "Successfully register an account",
                            Toast.LENGTH_SHORT
                        ).show()
                        redirectToLogin()
                    }
                    Status.ERROR -> {
                        binding.progressBar.isGone = true
                        Toast.makeText(
                            requireContext(),
                            result.message ?: "Unknown error occured",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Status.LOADING -> {
                        binding.progressBar.isVisible = true
                    }
                }
            }

        })
    }

    private fun redirectToLogin(){
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}