package com.example.dcj.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylibrary.model.Post
import com.example.mylibrary.usecase.GetRecentPosts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getRecentPosts : GetRecentPosts
) : ViewModel(){

    // (1) Livedata _recentPosts 는 결국 recentPosts 객체가 _recentPosts만 바라보고 있다는 것이다.
    // (2) 우리는 _recentPosts를 업데이트 할 것이다.
    // 이러면 우리는 바꿀 수 없는 LiveData를 가지고 UI에서 사용할 것이므로 안전하다.

    private val _recentPosts = MutableLiveData<MutableList<Post>>()
    val recentPosts: LiveData<MutableList<Post>> = _recentPosts

    init {
        loadRecentPosts()
    }

    private fun loadRecentPosts() {
        viewModelScope.launch {
            _recentPosts.value = getRecentPosts.execute()
        }
    }



}