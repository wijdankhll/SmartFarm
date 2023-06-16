package com.example.smartfarm.navigasi.ui.profil

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.smartfarm.R
import com.example.smartfarm.Response.UserPreference.UserPreference
import com.example.smartfarm.ViewmodelFactory.ViewModelFactory
import com.example.smartfarm.databinding.FragmentProfilBinding
import com.google.android.material.switchmaterial.SwitchMaterial

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class ProfilFragment : Fragment(R.layout.fragment_profil) {
    companion object{
        val EXTRA_NAME = "extra_name"
    }
//
    private var _binding: FragmentProfilBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val rootView = inflater.inflate(R.layout.fragment_profil, container, false)
        _binding = view?.let { FragmentProfilBinding.bind(it) }
        val switchTheme = rootView.findViewById<SwitchMaterial>(R.id.switch_theme)
        val pref = UserPreference.getInstance(requireContext().dataStore)
        val darkModeViewModel = ViewModelProvider(this, ViewModelFactory(pref, null)).get(
            ProfilViewModel::class.java
        )


        darkModeViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            darkModeViewModel.saveThemeSetting(isChecked)
        }

        return rootView
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}