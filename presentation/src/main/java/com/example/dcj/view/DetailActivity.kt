package com.example.dcj.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.dcj.R
import com.example.dcj.databinding.ActivityDetailBinding
import com.example.dcj.utils.FBAuth
import com.example.dcj.view.model.ContentDTO
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
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var getChallengeById: GetChallengeById

    private val mainviewmodel by viewModels<DetailActivityViewModel>()
    lateinit var myRef: DatabaseReference


    private var challengename: String? = null
    private var challengedetail: String? = null
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    lateinit var Challenge: Post
    var bookmarkFlag: Boolean = false

    //주협이 코드
    var firestore: FirebaseFirestore? = null
    var uid: String? = null
    var currentContentId: String? = null


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
            setupFavoriteImageView()
        } else {
            Log.e("DetailActivity", "No content ID provided in intent")
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

private fun setupFavoriteImageView() {
    binding.likeImage.setOnClickListener {
        if (uid == null) {
            // 사용자가 로그인하지 않았을 경우 Toast 메시지 출력
            Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
        } else {
            // 로그인한 사용자의 경우 좋아요 처리
            Log.d("DetailActivity", "Log in check success")
            currentContentId?.let { likeClick(it) }
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

    private fun likeClick(postId: String) {
        lateinit var post: Post

        CoroutineScope(Dispatchers.IO).launch {
            val post = async { getChallengeById.execute(postId) }.await()
            Log.d("DetailActivity", "can i get this ${post}")
            var updateLikeImage: Int? = null
            var newLikeCount: Int? = null

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
                                var uid = FirebaseAuth.getInstance().currentUser?.uid
                                Log.d("1111", "${uid}")

                                post?.let { dto ->
                                    Log.d("1111", "${dto.likes?.containsKey(uid)}")
                                    if (dto.likes?.containsKey(uid) == true) {
                                        // 좋아요가 이미 눌린 상태일 경우
                                        dto.likeCount -= 1
                                        dto.likes?.remove(uid)
                                        updateLikeImage = R.drawable.ic_favorite_border
                                    } else {
                                        // 좋아요가 안 눌린 상태일 경우
                                        dto.likeCount += 1
                                        uid?.let { uid -> dto.likes?.set(uid!!, true) }
                                        updateLikeImage = R.drawable.ic_favorite
                                    }
                                    newLikeCount = dto.likeCount
                                    transaction.set(documentReference, dto)
                                }
                            }?.addOnFailureListener { e ->
                                Log.e("DetailActivity", "Error updating like", e)
                            }
                        }
                    }
                    ?.addOnFailureListener { exception ->
                        // 오류 처리
                    }
            }

            withContext(Dispatchers.Main) {
                updateLikeImage?.let { binding.likeImage.setImageResource(it) }
                newLikeCount?.let { binding.likeNum.text = it.toString() }
            }
        }
    }




override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}