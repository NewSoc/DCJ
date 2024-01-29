package com.example.data.repository

import com.example.data.mapper.PostMapper
import com.example.data.repository.datasource.MainDataSource
import com.example.mylibrary.model.Post

import com.example.mylibrary.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainDataSource: MainDataSource
) : MainRepository {
    override suspend fun getRecentPost(): MutableList<Post>?  {
        return PostMapper.postReadMapper(mainDataSource.getRecentPost())
    }
}