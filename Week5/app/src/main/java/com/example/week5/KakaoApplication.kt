package com.example.week5

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kakao.sdk.common.KakaoSdk

class KakaoApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "ef3d75a5a2e244b5e3cf1f0218f72b31")
    }
}