package com.example.mylibrary.usecase

import com.example.mylibrary.repository.MainRepository
import javax.inject.Inject

class GetChallenge @Inject constructor(
    private val mainRepository : MainRepository
) {

    suspend fun getExerciseChallenge()= mainRepository.getExerciseChallenge()

    suspend fun getHobbyChallenge(){

    }

    suspend fun getEatChallenge(){

    }

    suspend fun getLifestyleChallenge(){

    }

    suspend fun getAllChallenge(){

    }

    suspend fun execute() = mainRepository.getRecentPost()
}