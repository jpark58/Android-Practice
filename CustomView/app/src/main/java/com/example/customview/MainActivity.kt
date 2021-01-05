package com.example.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val customView = CustomView("Hello Cotlin", this)
        frameLayout.addView(customView)
    }
}

class CustomView(text: String, context: Context): View(context){
    val text: String
    init{
        this.text = text
    }

    override fun onDraw(canvas: Canvas?){
        super.onDraw(canvas)
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 100f

        canvas?.drawText(text, 100f, 400f, paint)
    }
}