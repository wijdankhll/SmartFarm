package com.example.smartfarm.Login

import android.util.Log
import androidx.lifecycle.*
import com.example.smartfarm.API.ApiConfig
import com.example.smartfarm.Response.LoginResponse
import com.example.smartfarm.Response.UserPreference.UserPreference
import com.example.smartfarm.data.UserModel
import com.example.smartfarm.data.userRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel (private val pref: userRepository) : ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return pref.getUser()
    }

    fun login(user : UserModel) {
        viewModelScope.launch {
            pref.loginRequest(user.email, user.password)
            pref.userLogin()
        }
    }
}