package com.example.mylibrary.repository

import com.example.mylibrary.model.Post

interface MainRepository  {
    suspend fun getRecentPost() :List<Post>?
}