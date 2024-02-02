package com.example.dcj.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.dcj.R
import com.example.dcj.databinding.ActivityDetailBinding
import com.example.dcj.utils.FBAuth
import com.example.mylibrary.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import com.example.dcj.viewmodel.DetailActivityViewModel
import com.example.mylibrary.usecase.GetChallengeById
import com.example.mylibrary.usecase.GetImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var getChallengeById: GetChallengeById

    private val mainviewmodel by viewModels<DetailActivityViewModel>()
    lateinit var myRef: DatabaseReference


    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    var bookmarkFlag: Boolean = false

    //주협이 코드
    private var firestore: FirebaseFirestore? = null
    private var uid: String? = null
    private var currentContentId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()
        uid = FirebaseAuth.getInstance().currentUser?.uid
        currentContentId = intent.getStringExtra("challengeId")


        setupShareImageView()

        if (currentContentId != null) {
            Log.d("DetailActivity", "here done")
            setupLikeImageView()
        } else {
            Log.e("DetailActivity", "No content ID provided in intent")
        }








    // detail activity에 challenge가져와서 화면에 띄우기
        val pageid : String? = intent.getStringExtra("challengeId")
        mainviewmodel.loadDetailPosts(pageid)


        mainviewmodel.Post.observe(this) { post ->
            with(binding) {
                post.imageUrl?.let { GetImage.getImage(this@DetailActivity, it, imageView8) }
                textView3.text = post.name
                textView6.text = post.detail
            }
        }


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

            if(!bookmarkFlag){
                if (pageid != null) {
                    myRef.child(pageid).child(key).setValue(true)
                        .addOnSuccessListener {
                            bookmarkFlag = true
                            _binding!!.bookmarkBtn.setImageResource(R.drawable.bookmark_color)
                        }
                        .addOnFailureListener{
                            Log.e("bookmarkFlag", "Error updating database", it)
                        }
                }

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

    private fun setupLikeImageView() {
        // 초기 좋아요 상태와 갯수 설정
        currentContentId?.let { postId ->
            firestore?.collection("Challenge")
                ?.document()
                ?.collection("challenge")
                ?.document(postId)
                ?.get()
                ?.addOnSuccessListener { documentSnapshot ->
                    val post = documentSnapshot.toObject(Post::class.java)
                    post?.let {
                        val isLiked = it.likes?.containsKey(uid) ?: false
                        if (isLiked) {
                            binding.likeImage.setImageResource(R.drawable.ic_favorite)
                        } else {
                            binding.likeImage.setImageResource(R.drawable.ic_favorite_border)
                        }

                        // 좋아요 갯수 설정
                        val likeCount = it.likeCount
                        binding.likeNum.text = likeCount.toString()
                    }
                }
                ?.addOnFailureListener { e ->
                    Log.e("DetailActivity", "Error fetching like information", e)
                }
        }

        // 좋아요 버튼 클릭 리스너 설정
        binding.likeImage.setOnClickListener {
            if (uid == null) {
                Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
            } else {
                currentContentId?.let { postId ->
                    likeClick(postId)
                }
            }
        }
    }
    private fun likeClick(postId: String) {

        CoroutineScope(Dispatchers.IO).launch {
            val post = async { getChallengeById.execute(postId) }.await()

            if (post != null) {
                firestore?.collection("Challenge")
                    ?.document(post.category)
                    ?.collection("challenge")
                    ?.whereEqualTo("id", postId)
                    ?.get()
                    ?.addOnSuccessListener { querySnapshot ->
                        for (document in querySnapshot.documents) {
                            val documentReference = document.reference
                            firestore?.runTransaction { transaction ->

                                val snapshot = transaction.get(documentReference)
                                val currentPost = snapshot.toObject(Post::class.java)


                                currentPost?.let { post ->
                                    val uid = FirebaseAuth.getInstance().currentUser?.uid
                                    val likes = post.likes ?: hashMapOf()

                                    if (likes.containsKey(uid)) {
                                        // 좋아요 취소
                                        post.likeCount -= 1
                                        likes.remove(uid)
                                        binding.likeImage.setImageResource(R.drawable.ic_favorite_border)
                                        binding.likeNum.text = post.likeCount.toString()

                                    } else {
                                        // 좋아요 설정
                                        post.likeCount += 1
                                        uid?.let { likes[uid] = true }
                                        binding.likeImage.setImageResource(R.drawable.ic_favorite)
                                        binding.likeNum.text = post.likeCount.toString()
                                    }

                                    post.likes = likes
                                    transaction.set(documentReference, post)
                                }
                            }?.addOnFailureListener { e ->
                                Log.e("DetailActivity", "Error updating like", e)
                            }
                        }
                    }
                    ?.addOnFailureListener { exception ->
                        Log.e("DetailActivity", "Firestore query failed", exception)
                    }
            }
        }
    }
    private fun setupShareImageView() {
        binding.shareImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND_MULTIPLE)
            intent.type = "text/plain"

            val blogUrl = "https://tekken5953.tistory.com/"
            val content = "친구가 링크를 공유했어요!\n어떤 링크인지 들어가서 확인해볼까요?"
            intent.putExtra(Intent.EXTRA_TEXT,"$content\n\n$blogUrl")

            val chooserTitle = "친구에게 공유하기"
            startActivity(Intent.createChooser(intent, chooserTitle))
        }
    }
    override fun onDestroy() {
            super.onDestroy()
            _binding = null
    }
}