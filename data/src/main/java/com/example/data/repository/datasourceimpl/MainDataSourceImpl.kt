package com.example.data.repository.datasourceimpl

import android.util.Log
import android.widget.ImageView
import com.example.data.model.DataPost
import com.example.data.repository.datasource.MainDataSource
import com.example.mylibrary.model.Post
import com.example.mylibrary.model.review
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject
class MainDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : MainDataSource {
    override suspend fun getRecentPost(): MutableList<DataPost>? {



            val recentPosts : MutableList<DataPost> = mutableListOf()
        try {
            val result = firestore.collection("posts").get().await()
            for (document in result) {
                Log.d("datasource", "${document.id} => ${document.data}")

                // 각 DocumentSnapshot을 DataPost 객체로 변환
                // 예시로 id, title, content 등의 필드를 사용했습니다. 실제 필드에 맞게 조정하세요.
                val post = document.toObject(DataPost::class.java)
                recentPosts.add(post)
            }
        } catch (exception: Exception) {
            Log.d("datasource", "Error getting documents: ", exception)
        }





            return recentPosts

    }

    override suspend fun getChallengeById(id: String?): DataPost? {
        return try {
            val categories = listOf("생활", "식습관", "운동", "정서", "취미") // 검색할 카테고리 목록

            var result: DataPost? = null  // 결과를 담을 변수

            categories.forEach { category ->
                val querySnapshot = firestore.collection("Challenge")
                    .document(category)
                    .collection("challenge")
                    .whereEqualTo("id", id)
                    .get().await()

                if (!querySnapshot.isEmpty) {
                    // 쿼리 결과가 비어있지 않은 경우, 첫 번째 문서를 DataPost 객체로 변환
                    val tempResult = querySnapshot.documents.firstOrNull()?.toObject(DataPost::class.java)

                    if (tempResult != null) {
                        val documentId = querySnapshot.documents.firstOrNull()?.id // 문서의 ID 얻기
                        val commentList : MutableList<review> = mutableListOf()
                        val commentsSnapshot = documentId?.let {
                            firestore.collection("Challenge")
                                .document(category)
                                .collection("challenge")
                                .document(it)
                                .collection("comments")
                                .get().await()
                        }

                        if (commentsSnapshot != null) {
                            for (document in commentsSnapshot){
                                val comments = document.toObject(review::class.java)
                                commentList.add(comments)
                            }
                        }


                        tempResult.comments = commentList?.toMutableList()
                        result = tempResult

                        return@forEach  // 원하는 문서를 찾았으면 forEach 루프를 종료합니다.
                    }
                }
            }

            result  // 찾은 결과 반환
        } catch (exception: Exception) {
            Log.e("datasource", "Error getting documents: ", exception)
            null
        }
    }

    override suspend fun getExerciseChallenge(): MutableList<DataPost>? {
        val Challenge : MutableList<DataPost> = mutableListOf()
        try {
            val result = firestore.collection("Challenge").document("운동")
                .collection("challenge").get().await()
            for (document in result) {
                Log.d("datasource", "${document.id} => ${document.data}")

                // 각 DocumentSnapshot을 DataPost 객체로 변환
                // 예시로 id, title, content 등의 필드를 사용했습니다. 실제 필드에 맞게 조정하세요.
                val post = document.toObject(DataPost::class.java)
                Challenge.add(post)
            }
        } catch (exception: Exception) {
            Log.d("datasource", "Error getting documents: ", exception)
        }



        val sortedChallenge : MutableList<DataPost> = Challenge.sortedByDescending { it.likeCount }.toMutableList()


        return sortedChallenge
    }

    override suspend fun getHobbyChallenge(): MutableList<DataPost>? {
        val Challenge : MutableList<DataPost> = mutableListOf()
        try {
            val result = firestore.collection("Challenge").document("취미")
                .collection("challenge").get().await()
            for (document in result) {
                Log.d("datasource", "${document.id} => ${document.data}")

                // 각 DocumentSnapshot을 DataPost 객체로 변환
                // 예시로 id, title, content 등의 필드를 사용했습니다. 실제 필드에 맞게 조정하세요.
                val post = document.toObject(DataPost::class.java)
                Challenge.add(post)
            }
        } catch (exception: Exception) {
            Log.d("datasource", "Error getting documents: ", exception)
        }



        val sortedChallenge : MutableList<DataPost> = Challenge.sortedByDescending { it.likeCount }.toMutableList()


        return sortedChallenge
    }

    override suspend fun getEatChallenge(): MutableList<DataPost>? {
        val Challenge : MutableList<DataPost> = mutableListOf()
        try {
            val result = firestore.collection("Challenge").document("식습관")
                .collection("challenge").get().await()
            for (document in result) {
                Log.d("datasource", "${document.id} => ${document.data}")

                // 각 DocumentSnapshot을 DataPost 객체로 변환
                // 예시로 id, title, content 등의 필드를 사용했습니다. 실제 필드에 맞게 조정하세요.
                val post = document.toObject(DataPost::class.java)
                Challenge.add(post)
            }
        } catch (exception: Exception) {
            Log.d("datasource", "Error getting documents: ", exception)
        }



        val sortedChallenge : MutableList<DataPost> = Challenge.sortedByDescending { it.likeCount }.toMutableList()


        return sortedChallenge
    }

    override suspend fun getLifestyleChallenge(): MutableList<DataPost>? {
        val Challenge : MutableList<DataPost> = mutableListOf()
        try {
            val result = firestore.collection("Challenge").document("생활")
                .collection("challenge").get().await()
            for (document in result) {
                Log.d("datasource", "${document.id} => ${document.data}")

                // 각 DocumentSnapshot을 DataPost 객체로 변환
                // 예시로 id, title, content 등의 필드를 사용했습니다. 실제 필드에 맞게 조정하세요.
                val post = document.toObject(DataPost::class.java)
                Challenge.add(post)
            }
        } catch (exception: Exception) {
            Log.d("datasource", "Error getting documents: ", exception)
        }





        val sortedChallenge : MutableList<DataPost> = Challenge.sortedByDescending { it.likeCount }.toMutableList()


        return sortedChallenge
    }

    override suspend fun getAllChallenge(): MutableList<DataPost>? {
        val allChallenges: MutableList<DataPost> = mutableListOf()
        val categories = listOf("생활", "식습관", "운동", "정서", "취미")

        categories.forEach { category ->
            try {
                val result = firestore.collection("Challenge").document(category)
                    .collection("challenge").get().await()
                for (document in result) {
                    Log.d("datasource", "${document.id} => ${document.data}")
                    val post = document.toObject(DataPost::class.java)
                    allChallenges.add(post)
                }
            } catch (exception: Exception) {
                Log.d("datasource", "Error getting documents from category $category: ", exception)
            }
        }

        // likeCount를 기준으로 내림차순 정렬
        return allChallenges.sortedByDescending { it.likeCount }.toMutableList()
    }



}