package com.example.data.repository.datasource

import com.example.data.model.DataPost

interface MainDataSource {
    suspend fun getRecentPost() : MutableList<DataPost>?
}