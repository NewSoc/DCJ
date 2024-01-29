package com.example.dcj.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isInvisible
import com.example.dcj.R
import com.example.dcj.databinding.ActivityRegisterChallengeBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference

class RegisterChallengeActivity : AppCompatActivity() {

    lateinit var imageView : ImageView
    lateinit var progressBar : ProgressBar
    lateinit var root : DatabaseReference
    lateinit var reference : StorageReference
    lateinit var imageUri : Uri
    lateinit var binding : ActivityRegisterChallengeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterChallengeBinding.inflate(layoutInflater)

        binding.progressBar.isInvisible = false
        binding.imageView.setOnClickListener {
            val galleryintent : Intent

        }

        setContentView(binding.root)
    }


}