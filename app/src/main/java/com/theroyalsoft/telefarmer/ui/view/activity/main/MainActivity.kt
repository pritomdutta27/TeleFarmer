package com.theroyalsoft.telefarmer.ui.view.activity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.theroyalsoft.telefarmer.extensions.showToast
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.ActivityMainBinding
import com.theroyalsoft.telefarmer.utils.applyTransparentStatusBarAndNavigationBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun newIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTransparentStatusBarAndNavigationBar()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavSetup()

        // Api Call
        viewModel.fetchProfile()
        viewModel.fetchMetaData()
        ifApiGetError()
    }

    private fun bottomNavSetup() {
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_container) as NavHostFragment
        bottomNavigationView = binding.bottomnavigationbar
        bottomNavigationView.setupWithNavController(navHostFragment.navController)
        bottomNavigationView.selectedItemId = R.id.homeFragment
    }

    // Api Response
    private fun ifApiGetError() {
        lifecycleScope.launch {
            viewModel._errorFlow.collect { errorStr ->
                withContext(Dispatchers.Main) {
                    if (errorStr.isNotEmpty()) {
                        applicationContext.showToast(errorStr)
                    }
                }
            }
        }
    }
}