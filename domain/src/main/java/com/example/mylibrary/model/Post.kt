package com.example.mylibrary.model

import java.util.Date
data class Post(
    var name: String = "",
    var id: Int = 0,
    var uploader: String = "",
    var detail: String = "",
    var created_at: Date? = null,
    var updated_at: Date? = null,
    var imageUrl: String? = null
)
