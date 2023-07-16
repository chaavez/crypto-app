package com.example.cryptoapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.cryptoapp.R
import com.example.cryptoapp.adapter.ViewPagerAdapter
import com.example.cryptoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var adapterViewPager: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupBottomNavigation()
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewPager = binding.mainFragment
        adapterViewPager = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapterViewPager
        viewPager.isUserInputEnabled = false
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

    fun replaceFragment(fromFragment: Int, toFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction
            .replace(fromFragment, toFragment)
            .commit()
    }

    fun addFragment(fromFragment: Int, toFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction
            .add(fromFragment, toFragment)
            .addToBackStack(null)
            .commit()
    }

    fun pop() {
        val fragmentManager = supportFragmentManager
        fragmentManager.popBackStack()
    }
}