package com.example.mylibrary.model

import java.util.Date

data class review(
    val uploader_id : String,
    val id : String,
    val review : String,
    val upload_date : Date?,
)
