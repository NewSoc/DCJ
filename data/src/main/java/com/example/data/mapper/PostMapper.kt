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

    fun postMapper(dataPost: DataPost) : Post?{
        return dataPost?.let{
            Post(name = it.name,
                id = it.id,
                uploader = it.uploader,
                detail = it.detail,
                created_at = it.created_at,
                updated_at = it.updated_at,
                imageUrl = it.imageUrl)
        }
    }


}
