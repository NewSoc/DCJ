package com.example.dcj.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import com.example.dcj.R
import com.example.dcj.databinding.ActivityDetailBinding
import com.example.dcj.utils.FBAuth
import com.example.dcj.view.model.ContentDTO
import com.example.mylibrary.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import com.example.dcj.viewmodel.DetailActivityViewModel
import com.example.mylibrary.usecase.GetImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.ktx.Firebase


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val mainviewmodel by viewModels<DetailActivityViewModel>()
    lateinit var myRef : DatabaseReference


    private var challengename: String? = null
    private var challengedetail: String? = null
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    lateinit var Challenge : Post
    var bookmarkFlag : Boolean = false

    //주협이 코드
    var firestore : FirebaseFirestore? = null
    var uid : String? = null
    var currentContentId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //주협이 파일
        firestore = FirebaseFirestore.getInstance()
        uid = FirebaseAuth.getInstance().currentUser?.uid
        currentContentId = intent. getStringExtra("contentId")

        val favoriteImageView = findViewById<ImageView>(R.id.favorite_image)
        favoriteImageView.setOnClickListener {
            handleFavoriteClick(favoriteImageView)
        }



        // detail activity에 challenge가져와서 화면에 띄우기

        val pageid : String? = intent.getStringExtra("challengeId")
        mainviewmodel.loadRecentPosts(pageid)


        mainviewmodel.Post.observe(this, { post ->
            with(binding) {
                Log.d("detailactivitytest", "${post}")
                post.imageUrl?.let { GetImage.getImage(this@DetailActivity, it, imageView8) }
                textView3.text = post.name
                textView6.text = post.detail
            }
        })




        //화면 북마크
        val database = Firebase.database
        val key = "bookmarkIsTrue" // 가져올 데이터의 특정 키 값
        myRef = database.getReference("user").child(FBAuth.getUid())//현재 user의 uid 항목


        myRef.child(pageid!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {//페이지 데이터가 존재하면(페이지에 접속한 적이 있으면)
                    val userData = dataSnapshot.child(key).getValue(Boolean::class.java) // User 클래스로 데이터 매핑
                    if (userData == true) {//북마크 데이터가 true이면 색칠해줌
                        bookmarkFlag = true
                        _binding!!.bookmarkBtn.setImageResource(R.drawable.bookmark_color)
                    } else{
                        bookmarkFlag = false
                    }
                } else {
                    myRef.child(pageid).child(key).setValue(false)//현재 페이지에 처음 접속하면 pageid를 db에 추가
                    bookmarkFlag = false
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })//기존의 북마크 데이터를 불러오는 로직

        _binding!!.bookmarkBtn.setOnClickListener {//북마크 버튼을 누르면 그에 따른 처리를 수행함

            if(bookmarkFlag == false){
                myRef.child(pageid).child(key).setValue(true)
                    .addOnSuccessListener {
                        bookmarkFlag = true
                        _binding!!.bookmarkBtn.setImageResource(R.drawable.bookmark_color)
                    }
                    .addOnFailureListener{
                        Log.e("bookmarkFlag", "Error updating database", it)
                    }
            }
            else{
                myRef.child(pageid).child(key).setValue(false)
                    .addOnSuccessListener {
                        bookmarkFlag = false
                        _binding!!.bookmarkBtn.setImageResource(R.drawable.bookmark_white)
                    }
                    .addOnFailureListener{
                        Log.e("bookmarkFlag", "Error updating database", it)
                    }
            }
        }
    }


    //주협이의 추가 코드
    private fun handleFavoriteClick(favoriteImageView: ImageView) {
        currentContentId?.let { contentId ->
            val tsDoc = firestore?.collection("posts")?.document(contentId)

            tsDoc?.let { doc ->
                firestore?.runTransaction { transaction ->
                    val contentDTO = transaction.get(doc).toObject(ContentDTO::class.java)
                    contentDTO?.let { dto ->
                        val newFavoriteCount: Int
                        val isLiked = dto.favorites.containsKey(uid)

                        if (isLiked) {
                            newFavoriteCount = dto.favoriteCount - 1
                            dto.favorites.remove(uid)
                            favoriteImageView.setImageResource(R.drawable.ic_favorite_border)
                        } else {
                            newFavoriteCount = dto.favoriteCount + 1
                            uid?.let { userId -> dto.favorites[userId] = true }
                            favoriteImageView.setImageResource(R.drawable.ic_favorite)
                        }

                        dto.favoriteCount = newFavoriteCount
                        transaction.set(doc, dto)
                    } ?: throw com.google.firebase.firestore.FirebaseFirestoreException(
                        "ContentDTO not found or invalid format",
                        FirebaseFirestoreException.Code.ABORTED
                    )
                }?.addOnFailureListener { exception ->
                    // 트랜잭션 실패 시 처리
                    Log.e("DetailActivity", "Firestore transaction failed", exception)
                }
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}