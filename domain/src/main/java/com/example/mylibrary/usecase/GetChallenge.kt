package com.example.mylibrary.usecase

import com.example.mylibrary.repository.MainRepository
import javax.inject.Inject

class GetChallenge @Inject constructor(
    private val mainRepository : MainRepository
) {

    suspend fun getExerciseChallenge()= mainRepository.getExerciseChallenge()

    suspend fun getHobbyChallenge() = mainRepository.getHobbyChallenge()

    suspend fun getEatChallenge() = mainRepository.getEatChallenge()

    suspend fun getLifestyleChallenge()=  mainRepository.getLifestyleChallenge()

    suspend fun getAllChallenge() = mainRepository.getAllChallenge()

    suspend fun execute() = mainRepository.getRecentPost()
}