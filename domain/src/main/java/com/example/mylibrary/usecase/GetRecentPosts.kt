package com.example.mylibrary.usecase

import com.example.mylibrary.repository.MainRepository
import javax.inject.Inject

class GetRecentPosts @Inject constructor(
    private val mainRepository : MainRepository
) {
    suspend fun execute() = mainRepository.getRecentPost()
}


