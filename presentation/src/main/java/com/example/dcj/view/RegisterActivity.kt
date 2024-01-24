package com.example.dcj.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.dcj.R
import com.example.dcj.base.User
import com.example.dcj.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    lateinit var binding : ActivityRegisterBinding
    lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //인증 초기화
        mAuth = Firebase.auth

        //db 초기화
        mDbRef = Firebase.database.reference

        binding.btnRegisterFinish.setOnClickListener {

            val email = binding.editTxtEmail.text.toString().trim()
            val name = binding.editTxtName.text.toString().trim()
            val password = binding.editTxtPwd.text.toString().trim()

            signup(name, email, password)


        }

    }


    private fun signup(name :String, email : String, password : String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //성공시 할 행동
                    Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                    val intent : Intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                    startActivity(intent)

                } else {
                    //실패시 할 행동
                    Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                    Log.e("RegisterActivity", "회원가입 실패", task.exception)
                }
            }
    }

    private fun addUserToDatabase(name : String, email: String, uId:String){
        mDbRef.child("user").child(uId).setValue(User(name, email, uId))
    }

}