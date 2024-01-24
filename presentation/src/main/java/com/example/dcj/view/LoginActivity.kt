package com.example.dcj.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.dcj.R
import com.example.dcj.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase



class LoginActivity : AppCompatActivity() {

    lateinit var mAuth : FirebaseAuth
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = Firebase.auth

        binding.btnLogin.setOnClickListener {

            val email = binding.txtIdEmail.text.toString()
            val password = binding.txtPwd.text.toString()
            login(email, password)
        }




        //회원가입 버튼 event
        binding.btnRegister.setOnClickListener{
            val intent : Intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }



    }

    override fun onStart() {
        super.onStart()

    }

    private fun login(email : String, password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent :Intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                    finish()

                } else {

                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                    Log.d("Login", "Error : ${task.exception}")
                }
            }
    }

}