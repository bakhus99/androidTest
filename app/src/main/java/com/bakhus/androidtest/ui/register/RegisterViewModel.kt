package com.bakhus.androidtest.ui.register

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
class RegisterViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    private val _registerStatus = MutableLiveData<Resource<String>>()
    val registerStatus : LiveData<Resource<String>> = _registerStatus

    fun register(email:String,password:String,repeatedPassword:String,name:String,surname:String,middlename:String){
    _registerStatus.postValue(Resource.loading(null))
        if (email.isEmpty() || password.isEmpty() || repeatedPassword.isEmpty()|| name.isEmpty()
            || surname.isEmpty() || middlename.isEmpty()){
            _registerStatus.postValue(Resource.error("Please fill out all the fields", null))
            return
        }
        if (password != repeatedPassword){
            _registerStatus.postValue(Resource.error("Passwords don't match",null))
            return
        }
        viewModelScope.launch {
            val result = repository.register(email, password, name, surname, middlename)
            _registerStatus.postValue(result)
        }
    }

}