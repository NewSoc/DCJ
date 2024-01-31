package com.example.mylibrary.repository

import android.widget.ImageView
import com.example.mylibrary.model.Post

interface MainRepository  {
    suspend fun getRecentPost() : MutableList<Post>?

    suspend fun getExerciseChallenge() : MutableList<Post>?

    suspend fun getHobbyChallenge() : MutableList<Post>?

    suspend fun getEatChallenge() : MutableList<Post>?

    suspend fun getLifestyleChallenge() : MutableList<Post>?

    suspend fun getAllChallenge() : MutableList<Post>?


    suspend fun getChallengeById(id : String?) : Post?


}