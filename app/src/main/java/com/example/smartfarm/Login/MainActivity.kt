package com.example.smartfarm.Login

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
import com.example.smartfarm.Register.RegisterActivity
import com.example.smartfarm.Response.UserPreference.UserPreference
import com.example.smartfarm.ViewmodelFactory.ViewModelFactory
import com.example.smartfarm.data.UserModel
import com.example.smartfarm.data.userRepository
import com.example.smartfarm.databinding.ActivityMainBinding
import com.example.smartfarm.navigasi.NavigasiActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var user: UserModel
    private lateinit var usepref: UserPreference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(null, userRepository.getInstance(usepref))
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(this, { user ->
            this.user = user
        })
    }

    private fun setupAction() {
        binding.registLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-zA-Z0-9._-]+".toRegex()
            when {
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Masukkan email"
                }
                !email.matches(emailPattern) -> {
                    binding.emailEditTextLayout.error = "Format email salah"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Masukkan password"
                }
                email != user.email -> {
                    binding.emailEditTextLayout.error = "Email tidak sesuai"
                }
                password != user.password -> {
                    binding.passwordEditTextLayout.error = "Password tidak sesuai"
                }
                else -> {
                    loginViewModel.login(UserModel("", email,"", false, ""))
                    AlertDialog.Builder(this).apply {
                        setTitle("Yeah!")
                        setMessage("Anda berhasil login. Sudah tidak sabar untuk belajar ya?")
                        setPositiveButton("Lanjut") { _, _ ->
                            val intent = Intent(context, NavigasiActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
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
