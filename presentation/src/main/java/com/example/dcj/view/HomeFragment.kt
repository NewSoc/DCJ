package com.example.dcj.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dcj.R
import com.example.dcj.databinding.FragmentHomeBinding
import com.example.dcj.databinding.FragmentMyPageBinding
import com.example.dcj.databinding.HomeChallengeViewpagerBinding
import com.example.dcj.databinding.ItemViewpagerBinding
import com.google.android.material.tabs.TabLayoutMediator


private var listSize = 0
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private val slideHandler = Handler(Looper.getMainLooper())
    private lateinit var slideRunnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list1 = loadData1()
        val pagerAdapter1 = CustomPagerAdapter1(list1)
        binding.viewPager1.adapter = pagerAdapter1

        val list2 = loadData2()
        val pagerAdapter2 = CustomPagerAdapter2(list2)
        binding.viewPager2.adapter = pagerAdapter2

        val title = listOf("전체", "운동", "식습관", "생활", "정서", "취미")

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) {   tab, position ->
            tab.text = title[position]
        }.attach()

        setupAutoSlide()
    }

    fun loadData1() : List<Page1> {
        val pageList = mutableListOf<Page1>()
        for(page in 1 .. 5) {
            pageList.add(Page1("content", page))
            listSize += 1
        }
        return pageList
    }
    fun loadData2() : List<Page2> {
        val pageList = mutableListOf<Page2>()
        for(page in 1 .. 6) {
            pageList.add(Page2("공식챌린지", "Title", "오늘부터 시작"))
        }
        return pageList
    }

    private fun setupAutoSlide() {
        slideRunnable = Runnable {
            val adapter = binding.viewPager1.adapter
            adapter?.let {
                val nextItem = (binding.viewPager1.currentItem + 1) % it.itemCount
                binding.viewPager1.currentItem = nextItem
                slideHandler.postDelayed(slideRunnable, 3000)
            }
        }

        slideHandler.postDelayed(slideRunnable, 5000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 뷰가 파괴될 때 Handler 작업을 중지합니다.
        slideHandler.removeCallbacks(slideRunnable)
        _binding = null
    }

}



class CustomPagerAdapter1(val pageList:List<Page1>) : RecyclerView.Adapter<CustomPagerAdapter1.Holder>() {
    class Holder(val binding: ItemViewpagerBinding) : RecyclerView.ViewHolder(binding.root){
        fun setItem(page: Page1) {
            with(binding) {
                content.text = page.content
                pageNum.text = "${page.pageNum}/${listSize}"
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int = pageList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setItem(pageList[position])
        val page = pageList[position]

        holder.setItem(page)

        holder.itemView.setOnClickListener {
            Log.d("클릭", "Clicked slide number: ${page.pageNum}")
        }

    }

}
class CustomPagerAdapter2(val pageList:List<Page2>) : RecyclerView.Adapter<CustomPagerAdapter2.Holder>() {
    class Holder(val binding: HomeChallengeViewpagerBinding) : RecyclerView.ViewHolder(binding.root){
        fun setItem(page: Page2) {
            with(binding) {
                from1.text = page.company
                challengeTitle1.text = page.title
                startTime1.text = page.time
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = HomeChallengeViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.challenge1.setOnClickListener {
            Log.d("챌린지클릭", "Challenge 1 clicked")
        }

        binding.challenge2.setOnClickListener {
            Log.d("챌린지클릭", "Challenge 2 clicked")
        }

        binding.challenge3.setOnClickListener {
            Log.d("챌린지클릭", "Challenge 3 clicked")
        }

        binding.challenge4.setOnClickListener {
            Log.d("챌린지클릭", "Challenge 4 clicked")
        }

        return Holder(binding)
    }

    override fun getItemCount(): Int = pageList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setItem(pageList[position])
    }
}

data class Page1(val content:String, val pageNum:Int)
data class Page2(val company:String, val title:String, val time:String)
