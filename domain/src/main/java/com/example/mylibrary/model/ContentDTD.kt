package com.example.mylibrary.model

data class ContentDTD(
    var explain : String? = null,
    var imageUrl : String? = null,
    var uid : String? = null,
    var timestamp : Long? = null,
    var favoriteCout : Int = 0,
    var favorites : MutableMap<String, Boolean> = HashMap()) {
        data class Comment(
            var uid: String? = null,
            var userId: String? = null,
            var comment: String? = null,
            var timestamp: Long? = null
        )
    }




