package com.example.dcj.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dcj.R
import com.example.dcj.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase



class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)




    }

    override fun onStart() {
        super.onStart()

    }
}