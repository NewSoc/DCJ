package com.example.dcj.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.dcj.R
import com.example.dcj.databinding.ActivityDetailBinding
import com.example.dcj.utils.FBAuth
import com.example.dcj.viewmodel.MyPageViewModel
import com.example.mylibrary.model.Post
import com.example.mylibrary.usecase.GetChallengeById
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.example.dcj.viewmodel.DetailActivityViewModel
import com.example.mylibrary.usecase.GetImage
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    lateinit var myRef : DatabaseReference

    private val mainviewmodel by viewModels<DetailActivityViewModel>()


    private var challengename: String? = null
    private var challengedetail: String? = null
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    lateinit var Challenge : Post

    var bookmarkFlag : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // detail activity에 challenge가져와서 화면에 띄우기
        val id : String? = intent.getStringExtra("challengeId")
        mainviewmodel.loadRecentPosts(id)

        mainviewmodel.Post.observe(this, { post ->
            with(binding) {
                post.imageUrl?.let { GetImage.getImage(this@DetailActivity, it, imageView8) }
                textView3.text = post.name
                textView6.text = post.detail
            }
        })


        //화면 북마크
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
