package com.example.mylibrary.model

import java.util.Date
data class Post(
    var name: String = "",
    var id: String = "",
    var uploader: String = "",
    var detail: String = "",
    var created_at: Date? = null,
    var updated_at: Date? = null,
    var imageUrl: String? = null,
    var comments : MutableList<review>? = mutableListOf(),
    var likeCount: Int = 0,
    var likes : MutableMap<String, Boolean>? = HashMap(),
    var category : String = ""
)
