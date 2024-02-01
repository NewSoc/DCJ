package com.example.data.model

import com.example.mylibrary.model.review
import java.util.Date

data class DataPost @JvmOverloads constructor(
    var name: String = "",
    var id: String = "",
    var uploader: String = "",
    var detail: String = "",
    var created_at: Date? = null,
    var updated_at: Date? = null,
    var imageUrl: String? = null,
    var comments : MutableList<review>? = mutableListOf()
)
