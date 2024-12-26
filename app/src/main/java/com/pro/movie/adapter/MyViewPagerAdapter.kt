package com.pro.movie.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pro.movie.fragment.AccountFragment
import com.pro.movie.fragment.FavoriteFragment
import com.pro.movie.fragment.HistoryFragment
import com.pro.movie.fragment.HomeFragment

class MyViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> FavoriteFragment()
            2 -> HistoryFragment()
            3 -> AccountFragment()
            else -> HomeFragment()
        }
    }

    override fun getItemCount(): Int {
        return 4
    }
}