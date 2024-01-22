package com.example.dcj.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dcj.R
import com.example.dcj.databinding.ActivityMainBinding
import com.example.dcj.databinding.FragmentDetailBinding




class DetailFragment : Fragment() {

    private var challengename: String? = null
    private var challengedetail: String? = null
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            challengename = it.getString("challengeName")
            challengedetail = it.getString("challengeDetail")
            Log.d("input test1", "${challengename}")
        }
        Log.d("test", "여기는 되네요")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("DetailFragment", "Challenge Name: ${arguments?.getString("challengeName")}")
        Log.d("DetailFragment", "Challenge Detail: ${arguments?.getString("challengeDetail")}")

        Log.d("viewcreated test", "${arguments?.getString("challengeName", "none")}")
        arguments?.let {
            challengename = it.getString("challengeName")
            challengedetail = it.getString("challengeDetail")
            Log.d("input test2", "${challengename}")
        }


        with(binding){
            challengeName.text = "${challengename}"
            challengeDetail.text="${challengedetail}"
        }



    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}