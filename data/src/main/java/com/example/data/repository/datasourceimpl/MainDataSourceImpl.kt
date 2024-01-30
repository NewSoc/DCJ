package com.example.data.repository.datasourceimpl

import android.util.Log
import android.widget.ImageView
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
            val querySnapshot = firestore.collection("posts")
                .whereEqualTo("id", id) // 'id' 필드가 인자로 받은 id와 일치하는 문서 검색
                .get().await()

            if (!querySnapshot.isEmpty) {
                // 쿼리 결과가 비어있지 않은 경우, 첫 번째 문서를 DataPost 객체로 변환
                querySnapshot.documents.firstOrNull()?.toObject(DataPost::class.java)
            } else {
                // 일치하는 문서가 없는 경우 null 반환
                null
            }
        } catch (exception: Exception) {
            Log.e("datasource", "Error getting documents: ", exception)
            null
        }
    }


}