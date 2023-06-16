package com.example.smartfarm.navigasi

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.impl.Observable.Observer
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

import com.example.smartfarm.ImageView.ImageViewActivity


import com.example.smartfarm.R
import com.example.smartfarm.databinding.ActivityDetailBinding
import com.example.smartfarm.databinding.ActivityNavigasiBinding
import com.example.smartfarm.navigasi.ui.Camera.CameraActivity
import com.example.smartfarm.navigasi.ui.Camera.rotateFile
import java.io.File


class NavigasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigasiBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigasiBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val navView: BottomNavigationView = binding.navView

        binding.fab.setOnClickListener {
            val intent = Intent(this, ImageViewActivity::class.java)
            startActivity(intent)
        }

        val navController = findNavController(R.id.nav_host_fragment_activity_navigasi)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.kosong, R.id.navigation_notifications
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }


}