package com.example.data.mapper

import android.util.Log
import com.example.data.model.DataPost
import com.example.mylibrary.model.Post
import com.example.mylibrary.model.review

object PostMapper {
    fun postReadMapper(dataReadDataPost: MutableList<DataPost>?): MutableList<Post>? {
        return dataReadDataPost?.map { dataPost ->
            Post(
                name = dataPost.name,
                id = dataPost.id,
                uploader = dataPost.uploader,
                detail = dataPost.detail,
                created_at = dataPost.created_at,
                updated_at = dataPost.updated_at,
                imageUrl = dataPost.imageUrl
            )
        }?.toMutableList()
    }

    fun postMapper(dataPost: DataPost) : Post? {
        val commentsList = mutableListOf<review>() // 새로운 댓글 리스트를 생성합니다.

        dataPost.comments?.forEach { review ->
            Log.d("postmapper", "${review}")

            val copiedReview = review.copy() // review 객체를 복사합니다.
            commentsList.add(copiedReview) // 복사된 review를 새로운 리스트에 추가합니다.
        }

        return dataPost?.let {
            Post(
                name = it.name,
                id = it.id,
                uploader = it.uploader,
                detail = it.detail,
                created_at = it.created_at,
                updated_at = it.updated_at,
                imageUrl = it.imageUrl,
                comments = commentsList // 새로운 댓글 리스트를 할당합니다.
            )
        }
    }

}



