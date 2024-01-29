package com.example.dcj.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.dcj.R
import com.example.dcj.adapter.FragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CertificationFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_certification, container, false)

        viewPager = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tab_layout2)

        val adapter = FragmentAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "진행 중"
                1 -> "시작 전"
                else -> null
            }
        }.attach()

        val curBtn = view.findViewById<Button>(R.id.curBtn)
        curBtn.setOnClickListener {
            // Create an Intent to start DetailActivity
            val intent = Intent(activity, DetailActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}