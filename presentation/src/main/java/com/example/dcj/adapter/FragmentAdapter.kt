package com.example.dcj.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dcj.view.certification.InProgFragment
import com.example.dcj.view.certification.BeforeStartFragment

class FragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragmentList = listOf(
        InProgFragment(),
        BeforeStartFragment()
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}