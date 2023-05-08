package com.example.cryptoapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cryptoapp.features.home.HomeFragment
import com.example.cryptoapp.features.simulator.SimulatorFragment
import com.example.cryptoapp.features.wallet.WalletFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    private val fragmentsList: List<Fragment> = listOf(
        HomeFragment(),
        SimulatorFragment(),
        WalletFragment()
    )

    override fun getItemCount() = fragmentsList.size

    override fun createFragment(position: Int) = fragmentsList[position]
}