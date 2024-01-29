package com.example.data.repository.datasourceimpl

import android.util.Log
import com.example.data.model.DataPost
import com.example.data.repository.datasource.MainDataSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject
class MainDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : MainDataSource {
    override suspend fun getRecentPost(): MutableList<DataPost>? {


            val recentPosts2 : MutableList<DataPost> = mutableListOf()
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


}