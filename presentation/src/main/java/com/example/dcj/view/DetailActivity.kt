package com.example.dcj.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dcj.R
import com.example.dcj.databinding.ActivityDetailBinding
import com.example.dcj.utils.FBAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DetailActivity : AppCompatActivity() {
    lateinit var myRef : DatabaseReference
    private var challengename: String? = null
    private var challengedetail: String? = null
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    var bookmarkFlag : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 인텐트에서 데이터 가져오기
        challengename = intent.getStringExtra("challengeName")
        challengedetail = intent.getStringExtra("challengeDetail")

        with(binding){
            textView3.text = "${challengename}"
            textView6.text="${challengedetail}"
        }

        val database = Firebase.database
        val key = "bookmarkIsTrue" // 가져올 데이터의 특정 키 값

        myRef = database.getReference("user").child(FBAuth.getUid())

        myRef.child(key).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userData = dataSnapshot.getValue(Boolean::class.java) // User 클래스로 데이터 매핑
                    if (userData != null) {
                        Log.d("bookmarkFlag", userData.toString())
                        bookmarkFlag = true
                    }
                } else {
                    bookmarkFlag = false
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        _binding!!.bookmarkBtn.setOnClickListener {

            if(bookmarkFlag == false){
                _binding!!.bookmarkBtn.setImageResource(R.drawable.bookmark_color)
                myRef.child("bookmarkIsTrue").setValue(true)
                bookmarkFlag = true
            }
            else{
                _binding!!.bookmarkBtn.setImageResource(R.drawable.bookmark_white)
                myRef.child("bookmarkIsTrue").setValue(null)
                Log.d("bookmarkFlag", bookmarkFlag.toString())
                bookmarkFlag = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
