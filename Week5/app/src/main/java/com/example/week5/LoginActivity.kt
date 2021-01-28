package com.example.week5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.week5.databinding.ActivityLoginBinding
import com.example.week5.databinding.ActivityMainBinding
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {
    private lateinit var activityLoginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLoginBinding.inflate(layoutInflater)
        activityLoginBinding = binding
        setContentView(activityLoginBinding.root)

        activityLoginBinding.loginBtn.setOnClickListener {
            LoginClient.instance.loginWithKakaoAccount(this@LoginActivity) { token, error ->
                if (error != null) {
                    Log.d("로그", "로그인 클라이언트 요청 실패", error)
                }
                else if (token != null) {
                    setUserInfo()
                }
            }
        }
    }

    fun setUserInfo(){
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.d("로그", "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.d("로그", "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id", user.id)
                intent.putExtra("email", user.kakaoAccount?.email)
                intent.putExtra("nickname", user.kakaoAccount?.profile?.nickname)
                intent.putExtra("image", user.kakaoAccount?.profile?.thumbnailImageUrl)
                startActivity(intent)
            }
        }
    }
}