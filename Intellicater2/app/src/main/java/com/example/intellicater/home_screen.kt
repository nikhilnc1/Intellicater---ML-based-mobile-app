package com.example.intellicater

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.intellicater.databinding.ActivityHomeScreenBinding
import com.example.intellicater.fragment.Notification_bottom_fragment
//import com.example.intellicater.fragment.Notification_bottom_fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class home_screen : AppCompatActivity() {
    private lateinit var binding: ActivityHomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.fragmentContainerView)
        val bottomNavView = binding.bottomNavigationView
        bottomNavView.setupWithNavController(navController)

        binding.notificationButton.setOnClickListener {
            val bottomSheetDialog = Notification_bottom_fragment()
            bottomSheetDialog.show(supportFragmentManager,"Test")

        }

        // Uncomment if you have any other views you need to access via binding
        // binding.notificationButton.setOnClickListener { /* Your click listener code */ }
    }


}


