package com.example.data.model

import java.util.Date

data class DataPost @JvmOverloads constructor(
    var name: String = "",
    var id: Int = 0,
    var uploader: String = "",
    var detail: String = "",
    var created_at: Date? = null,
    var updated_at: Date? = null,
    var imageUrl: String? = null
)
