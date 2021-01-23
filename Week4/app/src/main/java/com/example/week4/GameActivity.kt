package com.example.week4

import android.graphics.Point
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi

class GameActivity : AppCompatActivity() {

    private lateinit var gameView: GameView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        val point = Point()
        windowManager.defaultDisplay.getSize(point)

        gameView = GameView(this, point.x, point.y)
        Log.d("로그2", "${point.x} // ${point.y}")

        setContentView(gameView)
    }

    override fun onPause() {
        super.onPause()
        gameView.pause()
    }

}