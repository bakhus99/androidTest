package com.bakhus.androidtest.ui.mainScreen

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bakhus.androidtest.R
import com.bakhus.androidtest.databinding.MainScreenFragmentBinding
import com.bakhus.androidtest.util.Status
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainScreenFragment : Fragment() {


    private val viewModel: MainScreenViewModel by viewModels()

    private var _binding: MainScreenFragmentBinding? = null
    private val binding get() = _binding!!
    private var photo:Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectPhoto()
        pickupDate()
        subscribeToObservers()
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            binding.circleImage.setImageURI(uri)
            photo = uri
        }
        binding.selectPhoto.setOnClickListener {
            getContent.launch("image/*")
        }
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val surname = binding.etSurname.text.toString()
            val birthPlace = binding.etPlaceOfBirth.text.toString()
            val birthDate =binding.tvSelectedDate.text.toString()
            val organization = binding.etOrganization.text.toString()
            val position = binding.etPosition.text.toString()
            val hobby = binding.auTvChooseSetvice.text.toString()

            photo?.let { it1 -> viewModel.updateProfile(avatar = it1,name, surname, birthPlace, birthDate, organization, position, hobby) }

        }
    }

    private fun subscribeToObservers(){
        viewModel.updateProfile.observe(viewLifecycleOwner, androidx.lifecycle.Observer { result ->
            result?.let {
                when(result.status){
                    Status.SUCCESS ->{
                        binding.progressBar.isGone = true
                        Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()
                    }
                    Status.ERROR -> {
                        binding.progressBar.isGone = true
                        Toast.makeText(requireContext(), result.message?:"An unknown error occured", Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING ->{
                        binding.progressBar.isVisible = true
                    }
                }
            }
        })
    }

    private fun pickupDate(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        binding.datePicker.setOnClickListener {
            val dpd = DatePickerDialog(requireContext(), { view, year, monthOfYear, dayOfMonth ->
                binding.tvSelectedDate.text =
                    ("" + dayOfMonth + "." + (monthOfYear + 1) + "." + year)
            }, year, month, day)
            dpd.show()
        }
    }

    override fun onResume() {
        super.onResume()
        val services = resources.getStringArray(R.array.interesting_themes)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, services)
        binding.auTvChooseSetvice.setAdapter(arrayAdapter)
    }

    private fun selectPhoto() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}