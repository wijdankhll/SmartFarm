package com.example.smartfarm.Register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfarm.API.ApiConfig
import com.example.smartfarm.Response.RegisterResponse
import com.example.smartfarm.Response.UserPreference.UserPreference
import com.example.smartfarm.data.UserModel
import com.example.smartfarm.data.userRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val pref: userRepository): ViewModel() {

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.registerRequest(user.name, user.email, user.password)
            pref.saveUser(user)
        }
    }


}