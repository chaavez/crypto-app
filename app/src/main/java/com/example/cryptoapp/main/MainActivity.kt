package com.example.cryptoapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.cryptoapp.R
import com.example.cryptoapp.adapter.ViewPagerAdapter
import com.example.cryptoapp.common.fragments.searchAsset.SearchAssetFragment
import com.example.cryptoapp.databinding.ActivityMainBinding
import com.example.cryptoapp.features.home.HomeFragment
import com.example.cryptoapp.features.simulator.SimulatorFragment
import com.example.cryptoapp.features.wallet.WalletFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var adapterViewPager: ViewPagerAdapter
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupBottomNavigation()
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewPager = binding.mainFragment
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
    }


    private fun setupBottomNavigation() {
        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position) {
                    0 -> binding.mainBottomNavigation.selectedItemId = R.id.menu_home
                    1 -> binding.mainBottomNavigation.selectedItemId = R.id.menu_simulator
                    2 -> binding.mainBottomNavigation.selectedItemId = R.id.menu_wallet
                }
            }
        })

        binding.mainBottomNavigation.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.menu_home -> viewPager.currentItem = 0
                R.id.menu_simulator -> viewPager.currentItem = 1
                R.id.menu_wallet -> viewPager.currentItem = 2
            }
            true
        }
    }

    fun showSearchAssetsFragment() {
        val searchAssetFragment = SearchAssetFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, searchAssetFragment)
            .addToBackStack(null)
            .commit()
    }
}