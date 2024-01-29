package com.example.dcj.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dcj.R
import com.example.dcj.adapter.CertificationAdapter
import com.example.dcj.base.Challenge

class BeforeStartFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CertificationAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_in_prog, container, false)

        recyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = CertificationAdapter()
        adapter.listData = loadData()
        recyclerView.adapter = adapter

        return view
    }

    fun loadData() : MutableList<Challenge>{
        val data:MutableList<Challenge> = mutableListOf()
    //fragment를 바꿨어요 qweqweqwasdzxchqwhuzxcqweasd
        for(no in 1..100){
            val title = "코끼리 ${no}"
            val challenge = Challenge(title)
            data.add(challenge)
        }

        return data
    }
}