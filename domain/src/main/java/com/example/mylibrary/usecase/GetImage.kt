package com.example.mylibrary.usecase

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object GetImage {
    fun getImage(context : Context, Url : String, imageview : ImageView){
        Glide.with(context)
            .load(Url)
            .into(imageview)
    }
}