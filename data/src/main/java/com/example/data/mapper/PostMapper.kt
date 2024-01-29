package com.example.data.mapper

import com.example.data.model.DataPost
import com.example.mylibrary.model.Post

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
}
