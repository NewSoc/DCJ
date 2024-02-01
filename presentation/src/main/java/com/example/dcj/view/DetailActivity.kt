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
        val id : String? = intent.getStringExtra("challengeId")
        mainviewmodel.loadDetailPosts(id)

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