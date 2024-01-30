package com.example.dcj.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.example.dcj.R
import com.example.dcj.databinding.ActivityDetailBinding
import com.example.dcj.viewmodel.MyPageViewModel
import com.example.mylibrary.model.Post
import com.example.mylibrary.usecase.GetChallengeById
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.example.dcj.viewmodel.DetailActivityViewModel
import com.example.mylibrary.usecase.GetImage


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val mainviewmodel by viewModels<DetailActivityViewModel>()

    private var challengename: String? = null
    private var challengedetail: String? = null
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    lateinit var Challenge : Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val id : String? = intent.getStringExtra("challengeId")
        mainviewmodel.loadRecentPosts(id)

        mainviewmodel.Post.observe(this, { post ->
            with(binding) {
                post.imageUrl?.let { GetImage.getImage(this@DetailActivity, it, imageView8) }
                textView3.text = post.name
                textView6.text = post.detail
            }
        })



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}