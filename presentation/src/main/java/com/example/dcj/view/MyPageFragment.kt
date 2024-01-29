package com.example.dcj.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dcj.R
import com.example.dcj.adapter.RecentChallengeAdapter
import com.example.dcj.databinding.FragmentMyPageBinding
import com.example.dcj.viewmodel.MyPageViewModel
import com.example.mylibrary.model.Post
import com.example.mylibrary.usecase.GetRecentPosts
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import javax.inject.Inject



class MyPageFragment : Fragment() {

    private lateinit var binding: FragmentMyPageBinding
    private val mainviewmodel by activityViewModels<MyPageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 데이터 바인딩을 사용하여 레이아웃을 인플레이트합니다.
        binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainviewmodel.recentPosts.observe(viewLifecycleOwner, Observer { recentPosts ->
            if (recentPosts.isNotEmpty()) {
                Log.d("mypagefragment", "not empty")
                val customAdapter = recentPosts?.let {
                    RecentChallengeAdapter(it, object : RecentChallengeAdapter.OnItemClickListener {
                        override fun onItemClick(challenge: Post) {
                            Log.d("mypagefragment", "${challenge.name}")

                            // 번들에 넣자
                            val bundle = Bundle().apply{
                                putString("challengeName", "${challenge.name}")
                                putString("challengeDetail", challenge.detail)
                            }

                            // DetailFragment에 Bundle 설정
                            // DetailFragment의 새 인스턴스를 생성합니다.


                            // 프래그먼트 매니저와 트랜잭션을 사용하여 프래그먼트 교체
                            val intent = Intent(requireActivity(), DetailActivity::class.java)
                            intent.putExtras(bundle) // 필요한 데이터를 번들로 전달할 수 있음
                            startActivity(intent)

                        }
                    })
                }
                binding.recentRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.recentRecyclerview.adapter = customAdapter
            }
        })
    }


}