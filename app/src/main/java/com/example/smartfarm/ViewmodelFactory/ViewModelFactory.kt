package com.example.smartfarm.ViewmodelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartfarm.Login.LoginViewModel
import com.example.smartfarm.Register.RegisterViewModel
import com.example.smartfarm.Response.UserPreference.UserPreference
import com.example.smartfarm.data.userRepository
import com.example.smartfarm.navigasi.ui.profil.ProfilViewModel


class ViewModelFactory(private val pref: UserPreference?, private val pres: userRepository?) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfilViewModel::class.java)) {
            return ProfilViewModel(pref!!) as T
        } else if ( modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(pres!!) as T
        } else if ( modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(pres!!) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
//        fun getInstance(context: Context): ViewModelFactory =
//            instance ?: synchronized(this) {
//                instance ?: ViewModelFactory(null, Injection.provideRepository(context))
//            }.also { instance = it }
    }
}