package com.example.mylibrary.usecase

import com.example.mylibrary.repository.MainRepository
import javax.inject.Inject

class GetChallengeById @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend fun execute(id : String?) = mainRepository.getChallengeById(id)
}