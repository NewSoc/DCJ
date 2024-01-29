package com.example.dcj.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isInvisible
import com.example.dcj.R
import com.example.dcj.databinding.ActivityRegisterChallengeBinding
import com.example.mylibrary.model.Post
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.Date
import java.util.UUID

class RegisterChallengeActivity : AppCompatActivity() {

    lateinit var imageView : ImageView
    lateinit var progressBar : ProgressBar
    lateinit var root : FirebaseFirestore
    private var imageUri : Uri? = null
    lateinit var binding : ActivityRegisterChallengeBinding

    private val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // 갤러리에서 선택한 이미지 처리
            imageUri = result.data?.data
            // URI를 사용하여 이미지뷰에 이미지를 로드하거나 필요한 대로 처리하세요
            imageView.setImageURI(imageUri)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterChallengeBinding.inflate(layoutInflater)
        imageView = binding.imageView
        progressBar = binding.progressBar
        binding.progressBar.isInvisible = true


        //이미지 클릭 이벤트
        binding.imageView.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
            }
            activityResult.launch(galleryIntent)
        }

        //업로드 버튼 클릭
        binding.uploadBtn.setOnClickListener {
            imageUri?.let {
                // imageUri가 null이 아닌 경우에만 uploadToStorage 함수 호출
                uploadToStorage(it)
            } ?: run {
                // imageUri가 null인 경우
                Toast.makeText(getApplicationContext(), "사진을 선택해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        setContentView(binding.root)
    }




    fun uploadToStorage(uri : Uri){
        // Storage 참조 초기화
        val filename = UUID.randomUUID().toString() // 고유 파일명 생성
        val storageRef = FirebaseStorage.getInstance().getReference("/images/$filename")

        progressBar.isInvisible = false // 업로드 중 프로그레스바 보이기

        storageRef.putFile(uri)
            .addOnSuccessListener { taskSnapshot ->
                // 업로드 성공시
                taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { downloadUri ->
                    val imageUrl = downloadUri.toString()
                    savePostToFirestore(imageUrl) // Firestore에 저장하는 함수 호출
                }
            }
            .addOnFailureListener {
                // 업로드 실패시
                progressBar.isInvisible = true
                Toast.makeText(this, "업로드 실패: ${it.message}", Toast.LENGTH_SHORT).show()
            }


    }

    fun savePostToFirestore(imageUrl : String){
        // Firestore 참조 초기화
        val db = FirebaseFirestore.getInstance()
        val postRef = db.collection("posts")


            val post = Post(
                name = "${binding.challengeName.toString()}", // 필요한 값으로 변경
                id = generateUniqueId(), // 고유 ID 생성 함수
                uploader = "김도형", // 현재 사용자 정보나 입력값으로 대체
                detail = "${binding.challengeDetail.toString()}", // 입력값으로 대체
                created_at = Date(),
                updated_at = Date(),
                imageUrl = imageUrl
            )





        // Firestore에 Post 객체 저장
        postRef.add(post)
            .addOnSuccessListener {
                progressBar.isInvisible = true // 프로그레스바 숨기기
                Toast.makeText(this, "게시물이 저장되었습니다.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                progressBar.isInvisible = true
                Toast.makeText(this, "게시물 저장 실패: ${it.message}", Toast.LENGTH_SHORT).show()
            }


    }


    fun generateUniqueId(): String {
        val currentTime = System.currentTimeMillis()
        val uuid = UUID.randomUUID().toString()
        return "$currentTime-$uuid"
    }

}