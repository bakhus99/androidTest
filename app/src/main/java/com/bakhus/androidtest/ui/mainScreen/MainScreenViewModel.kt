package com.bakhus.androidtest.ui.mainScreen

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bakhus.androidtest.repository.Repository
import com.bakhus.androidtest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _updateProfile = MutableLiveData<Resource<String>>()
    val updateProfile : LiveData<Resource<String>> = _updateProfile

    fun updateProfile(avatar: Uri, name:String, surname: String, birthPlace:String, birthDate:String, organization:String, position:String, hobby:String){
        _updateProfile.postValue(Resource.loading(null))
        if(name.isEmpty() || surname.isEmpty() || birthPlace.isEmpty() || hobby.isEmpty() || birthDate.isEmpty()){
            _updateProfile.postValue(Resource.error("Please fill out the fields",null))
        }
        viewModelScope.launch {
            val result = repository.updateProfile(avatar, name, surname, birthPlace, birthDate, organization, position, hobby)
            _updateProfile.postValue(result)
        }
    }




}