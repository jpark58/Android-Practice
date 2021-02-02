package com.example.week5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import com.example.week5.databinding.ActivityLoginBinding
import com.example.week5.databinding.ActivityMainBinding
import com.example.week5.utils.API
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var activityLoginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLoginBinding.inflate(layoutInflater)
        activityLoginBinding = binding
        setContentView(activityLoginBinding.root)

        lottie.playAnimation()


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



        supportActionBar?.hide()
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

                UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                    if (error != null) {
                        Log.e("로그", "토큰 정보 보기 실패", error)
                    }
                    else if (tokenInfo != null) {
                        Log.i("로그", "토큰 정보 보기 성공" +
                                "\n회원번호: ${tokenInfo.id}" +
                                "\n만료시간: ${tokenInfo.expiresIn} 초")
                       API.token = tokenInfo.id.toString()
                    }
                }

                startActivity(intent)
            }
        }
    }
}