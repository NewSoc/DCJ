package com.example.dcj.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylibrary.model.Post
import com.example.mylibrary.usecase.GetChallengeById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailActivityViewModel @Inject constructor(
    private val getChallengeById: GetChallengeById
) :ViewModel() {
    private val _Post = MutableLiveData<Post>()
    val Post: LiveData<Post> = _Post

//
    /*
    private val _recentPosts = MutableLiveData<MutableList<Post>>()
    val recentPosts: LiveData<MutableList<Post>> = _recentPosts
      */

     fun loadDetailPosts(id : String?) {
        viewModelScope.launch {
            _Post.value = getChallengeById.execute(id)
        }
    }



}