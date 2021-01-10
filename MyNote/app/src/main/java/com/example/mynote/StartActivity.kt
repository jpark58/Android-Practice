package com.example.mynote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("로그", "StartActivity - onCreate")
        setContentView(R.layout.activity_start)
        nameBtn.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            var username = username.text.toString()
            intent.putExtra("username", username)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("로그", "StartActivity - onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("로그", "StartActivity - onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("로그", "StartActivity - onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("로그", "StartActivity - onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("로그", "StartActivity - onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("로그", "StartActivity - onDestroy")
    }
}