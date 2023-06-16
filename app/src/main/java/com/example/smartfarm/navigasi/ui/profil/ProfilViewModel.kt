package com.example.smartfarm.navigasi.ui.profil

import androidx.lifecycle.*
import com.example.smartfarm.Response.UserPreference.UserPreference
import kotlinx.coroutines.launch

class ProfilViewModel(private val pref: UserPreference) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}