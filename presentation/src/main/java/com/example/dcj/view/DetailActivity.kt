package com.example.dcj.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dcj.R
import com.example.dcj.databinding.ActivityDetailBinding
import com.google.firebase.firestore.FirebaseFirestore

class DetailActivity : AppCompatActivity() {
    private var challengename: String? = null
    private var challengedetail: String? = null
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 인텐트에서 데이터 가져오기
        challengename = intent.getStringExtra("challengeName")
        challengedetail = intent.getStringExtra("challengeDetail")

        with(binding){
            textView3.text = "${challengename}"
            textView6.text="${challengedetail}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
