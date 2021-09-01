package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

    navController = this.findNavController(R.id.navHostFragment)
    NavigationUI.setupActionBarWithNavController(this, navController)
  }

  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp()
  }
}
