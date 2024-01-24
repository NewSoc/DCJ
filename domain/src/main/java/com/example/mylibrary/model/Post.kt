package com.example.mylibrary.model

import java.util.Date

data class Post(

    //데이터베이스에서 유저 검색할 대 스는 id가 될 예정
    var id : Int,

    // 날짜 필요하지 포스트 날짜?
    var uploader : String,

    // detail 세부 텍스트
    var text : String,
    var created_at : Date,
    var updated_at : Date,
    var imageUrl : String
)
