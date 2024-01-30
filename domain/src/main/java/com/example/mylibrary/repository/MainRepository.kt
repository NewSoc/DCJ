package com.example.mylibrary.repository

import android.widget.ImageView
import com.example.mylibrary.model.Post

interface MainRepository  {
    suspend fun getRecentPost() : MutableList<Post>?

    suspend fun getChallengeById(id : String?) : Post?


}