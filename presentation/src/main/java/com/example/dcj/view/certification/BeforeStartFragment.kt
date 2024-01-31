package com.example.dcj.view.certification

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dcj.R
import com.example.dcj.adapter.CertificationAdapter
import com.example.dcj.base.Challenge
import com.example.mylibrary.model.Post
import com.example.mylibrary.usecase.GetRecentPosts
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BeforeStartFragment : Fragment() {

    @Inject
    lateinit var getRecentPosts: GetRecentPosts
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
        lifecycleScope.launch {
            val mytest : MutableList<Post>? = getRecentPosts.execute()
            Log.d("zzzz", "${mytest}")
        }

        return view
    }

    fun loadData() : MutableList<Challenge>{
        val data:MutableList<Challenge> = mutableListOf()

        for(no in 1..100){
            val username = "코끼리"
            val title = "코끼리 수면법"
            val category = "수면 루틴"
            val timestart = "오늘부터"
            val challenge = Challenge(username, title, category, timestart)
            data.add(challenge)
        }

        return data
    }
}