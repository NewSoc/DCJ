package com.example.dcj.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isInvisible
import androidx.lifecycle.lifecycleScope
import com.example.dcj.R
import com.example.dcj.databinding.ActivityRegisterChallengeBinding
import com.example.mylibrary.model.Post
import com.example.mylibrary.usecase.GetRecentPosts
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class RegisterChallengeActivity : AppCompatActivity() {

    @Inject
    lateinit var getRecentPosts: GetRecentPosts

    lateinit var imageView : ImageView
    lateinit var progressBar : ProgressBar
    lateinit var root : FirebaseFirestore
    private var imageUri : Uri? = null
    lateinit var binding : ActivityRegisterChallengeBinding
    lateinit var category : String

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



        //스피너 만들기

        val testList = resources.getStringArray(R.array.testList)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, testList)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                category = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }



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




        lifecycleScope.launch {
            val mytest : MutableList<Post>? = getRecentPosts.execute()
            Log.d("zzzz", "${mytest}")
            if (mytest != null && mytest.isNotEmpty()) {
                binding.testtest.setText(mytest[0].name)
            } else {
                // 리스트가 비어있는 경우의 처리
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

//흠... post에 넣을까말까...음...
        when(category){
            "생활" -> {
                val postRef = db.collection("Challenge").document("생활").collection("challenge")
                val post = Post(
                    name = "${binding.challengeName.text.toString()}", // 필요한 값으로 변경
                    id = generateUniqueId(), // 고유 ID 생성 함수
                    uploader = "김도형", // 현재 사용자 정보나 입력값으로 대체
                    detail = "${binding.challengeDetail.text.toString()}", // 입력값으로 대체
                    created_at = Date(),
                    updated_at = Date(),
                    imageUrl = imageUrl
                )
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
            "식습관" ->{
                val postRef = db.collection("Challenge").document("식습관").collection("challenge")
                val post = Post(
                    name = "${binding.challengeName.text.toString()}", // 필요한 값으로 변경
                    id = generateUniqueId(), // 고유 ID 생성 함수
                    uploader = "김도형", // 현재 사용자 정보나 입력값으로 대체
                    detail = "${binding.challengeDetail.text.toString()}", // 입력값으로 대체
                    created_at = Date(),
                    updated_at = Date(),
                    imageUrl = imageUrl
                )
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
            "운동" ->{
                val postRef = db.collection("Challenge").document("운동").collection("challenge")
                val post = Post(
                    name = "${binding.challengeName.text.toString()}", // 필요한 값으로 변경
                    id = generateUniqueId(), // 고유 ID 생성 함수
                    uploader = "김도형", // 현재 사용자 정보나 입력값으로 대체
                    detail = "${binding.challengeDetail.text.toString()}", // 입력값으로 대체
                    created_at = Date(),
                    updated_at = Date(),
                    imageUrl = imageUrl
                )
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
            "정서" ->{
                val postRef = db.collection("Challenge").document("정서").collection("challenge")
                val post = Post(
                    name = "${binding.challengeName.text.toString()}", // 필요한 값으로 변경
                    id = generateUniqueId(), // 고유 ID 생성 함수
                    uploader = "김도형", // 현재 사용자 정보나 입력값으로 대체
                    detail = "${binding.challengeDetail.text.toString()}", // 입력값으로 대체
                    created_at = Date(),
                    updated_at = Date(),
                    imageUrl = imageUrl
                )
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
            "취미" ->{
                val postRef = db.collection("Challenge").document("취미").collection("challenge")
                val post = Post(
                    name = "${binding.challengeName.text.toString()}", // 필요한 값으로 변경
                    id = generateUniqueId(), // 고유 ID 생성 함수
                    uploader = "김도형", // 현재 사용자 정보나 입력값으로 대체
                    detail = "${binding.challengeDetail.text.toString()}", // 입력값으로 대체
                    created_at = Date(),
                    updated_at = Date(),
                    imageUrl = imageUrl
                )
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
            else -> {
                Toast.makeText(this, "게시물 저장 실패", Toast.LENGTH_SHORT).show()

            }
        }






    }


    fun generateUniqueId(): String {
        val currentTime = System.currentTimeMillis()
        val uuid = UUID.randomUUID().toString()
        return "$currentTime-$uuid"
    }

}