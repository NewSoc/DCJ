package com.example.data.repository.datasource

import android.widget.ImageView
import com.example.data.model.DataPost

interface MainDataSource {
    suspend fun getRecentPost() : MutableList<DataPost>?
    suspend fun getChallengeById(id : String?) : DataPost?

    suspend fun getExerciseChallenge() : MutableList<DataPost>?

    suspend fun getHobbyChallenge() : MutableList<DataPost>?

    suspend fun getEatChallenge() : MutableList<DataPost>?

    suspend fun getLifestyleChallenge() : MutableList<DataPost>?

    suspend fun getAllChallenge() : MutableList<DataPost>?

}