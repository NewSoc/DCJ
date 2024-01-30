package com.example.data.repository.datasource

import android.widget.ImageView
import com.example.data.model.DataPost

interface MainDataSource {
    suspend fun getRecentPost() : MutableList<DataPost>?
    suspend fun getChallengeById(id : String?) : DataPost?
}