package com.example.mylibrary.usecase

import com.google.firebase.auth.FirebaseAuth

object CheckLogin {

    fun check_login() : Boolean{
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            return true
        } else {
            return false
        }
    }
}