package com.example.dcj.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dcj.R
import com.example.dcj.adapter.RecentChallengeAdapter
import com.example.dcj.databinding.FragmentMyPageBinding
import com.example.mylibrary.model.DomainChallenge
import com.example.mylibrary.usecase.GetChallengeRecent


class MyPageFragment : Fragment() {

    private lateinit var binding: FragmentMyPageBinding

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

        val data:MutableList<DomainChallenge> = GetChallengeRecent.execute()

        val customAdapter = RecentChallengeAdapter(data, object : RecentChallengeAdapter.OnItemClickListener {
            override fun onItemClick(challenge: DomainChallenge) {



                // 번들에 넣자
                val bundle = Bundle().apply{
                    putString("challengeName", "${challenge.name}")
                    putString("challengeDetail", challenge.detail)
                }

                // DetailFragment에 Bundle 설정
                // DetailFragment의 새 인스턴스를 생성합니다.


                // 프래그먼트 매니저와 트랜잭션을 사용하여 프래그먼트 교체
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.main_container, DetailFragment().apply{
                    arguments = bundle
                }) // 'fragment_container'는 교체할 뷰의 ID
                transaction.addToBackStack(null) // 백 스택에 추가
                transaction.commit()


            }
        })

        binding.recentRecyclerview.adapter = customAdapter

        binding.recentRecyclerview.layoutManager = (LinearLayoutManager(this.activity , RecyclerView.HORIZONTAL, false ))



    }


}