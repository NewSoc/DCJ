package com.example.data.repository

import android.widget.ImageView
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

    override suspend fun getExerciseChallenge(): MutableList<Post>? {
       return PostMapper.postReadMapper(mainDataSource.getExerciseChallenge())
    }

    override suspend fun getHobbyChallenge(): MutableList<Post>? {
       return PostMapper.postReadMapper(mainDataSource.getHobbyChallenge())
    }

    override suspend fun getEatChallenge(): MutableList<Post>? {
       return PostMapper.postReadMapper(mainDataSource.getEatChallenge())
    }

    override suspend fun getLifestyleChallenge(): MutableList<Post>? {
       return PostMapper.postReadMapper(mainDataSource.getLifestyleChallenge())
    }

    override suspend fun getAllChallenge(): MutableList<Post>? {
      return  TODO("Not yet implemented")
    }

    override suspend fun getChallengeById(id: String?): Post? {
       return mainDataSource.getChallengeById(id)?.let { PostMapper.postMapper(it) }
    }


}