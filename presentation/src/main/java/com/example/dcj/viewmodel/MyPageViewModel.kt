package com.example.dcj.viewmodel

import androidx.lifecycle.ViewModel
import com.example.dcj.base.Post
import com.example.mylibrary.usecase.GetRecentPosts
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getRecentPosts : GetRecentPosts
) : ViewModel() {
    lateinit var recent_post_lists : MutableList<Post>


}