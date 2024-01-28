package com.example.dcj.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.dcj.R
import com.example.dcj.databinding.ActivityMainBinding
import com.example.mylibrary.usecase.CheckLogin
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContentView(binding.root)

        setBottomNavigationView()

        // 앱 초기 실행 시 홈화면으로 설정
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.homeFragment
        }
    }

    fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, HomeFragment()).commit()
                    true
                }
                R.id.calenderFragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, CertificationFragment()).commit()
                    true
                }
                R.id.myPageFragment -> {
                    if(CheckLogin.check_login() == true){
                        supportFragmentManager.beginTransaction().replace(R.id.main_container, MyPageFragment()).commit()
                        true
                    }
                    if(CheckLogin.check_login() == false){
                        supportFragmentManager.beginTransaction().replace(R.id.main_container, LoginFragment()).commit()
                        true
                    }
                    false
                }
                else -> false
            }
        }
    }
}