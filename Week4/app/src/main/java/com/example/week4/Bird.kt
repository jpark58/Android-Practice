package com.example.week4

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.example.week4.GameView.Companion.screenRatioX
import com.example.week4.GameView.Companion.screenRatioY

class Bird(res: Resources) {
    var wasShot = true
    var speed = 20
    var x = 0
    var y: Int? = null
    var width: Int? = null
    var height: Int? = null
    var birdCounter = 1

    lateinit var bird1: Bitmap
    lateinit var bird2: Bitmap
    lateinit var bird3: Bitmap
    lateinit var bird4: Bitmap

    init{
        bird1 = BitmapFactory.decodeResource(res, R.drawable.ufo)
//        bird2 = BitmapFactory.decodeResource(res, R.drawable.bird2)
//        bird3 = BitmapFactory.decodeResource(res, R.drawable.bird3)
//        bird4 = BitmapFactory.decodeResource(res, R.drawable.bird4)

        width = bird1.width
        height = bird1.height

        width = width!! / 3
        height = height!! / 3

        width = width!! * screenRatioX!!.toInt()
        height = height!! * screenRatioY!!.toInt()

        bird1 = Bitmap.createScaledBitmap(bird1, width!!, height!!, false)
//        bird2 = Bitmap.createScaledBitmap(bird2, width!!, height!!, false)
//        bird3 = Bitmap.createScaledBitmap(bird3, width!!, height!!, false)
//        bird4 = Bitmap.createScaledBitmap(bird4, width!!, height!!, false)

        y = -height!!
    }

    fun getBird(): Bitmap{
        if (birdCounter == 1){
            birdCounter++
            return bird1
        }
        if (birdCounter == 2){
            birdCounter++
            return bird1
        }
        if (birdCounter == 3){
            birdCounter++
            return bird1
        }

        birdCounter = 1
        return bird1
    }

    fun getCollisionShape(): Rect {
        return Rect(x, y!!, x + width!!, y!! + height!!)
    }
}