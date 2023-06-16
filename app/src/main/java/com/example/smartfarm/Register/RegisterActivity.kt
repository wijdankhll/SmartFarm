package com.example.smartfarm.Register

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.smartfarm.Detail.DetailActivity
import com.example.smartfarm.Login.MainActivity
import com.example.smartfarm.R

import com.example.smartfarm.Response.UserPreference.UserPreference
import com.example.smartfarm.ViewmodelFactory.ViewModelFactory
import com.example.smartfarm.data.UserModel
import com.example.smartfarm.data.userRepository
import com.example.smartfarm.databinding.ActivityRegisterBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var signupViewModel: RegisterViewModel
    private lateinit var usepref: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        usepref = UserPreference(dataStore)
        signupViewModel = ViewModelProvider(
            this,
            ViewModelFactory(null, userRepository.getInstance(usepref))
        )[RegisterViewModel::class.java]
    }

    private fun setupAction() {
        binding.loginLink.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.registerButton.setOnClickListener {
            val name = binding.usernameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            val bundle = Bundle()
            bundle.putString(DetailActivity.EXTRA_NAME, name)


            when {
                name.isEmpty() -> {
                    binding.usernameEditTextLayout.error = "Masukkan email"
                }
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Masukkan email"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Masukkan password"
                }
                else -> {
                    signupViewModel.saveUser(UserModel(name, email, password, false, ""))
                    AlertDialog.Builder(this).apply {
                        setTitle("Yeah!")
                        setMessage("Akunnya sudah jadi nih. Yuk, login dan belajar coding.")
                        setPositiveButton("Lanjut") { _, _ ->
                            finish()
                        }
                        create()
                        show()
                    }
                }
            }
        }
    }
}