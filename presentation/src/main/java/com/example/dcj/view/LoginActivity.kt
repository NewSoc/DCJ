package com.example.dcj.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dcj.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Initialize Firebase Auth
        auth = Firebase.auth

        setContentView(R.layout.activity_login)
    }

    override fun onStart() {
        super.onStart()
    }
}