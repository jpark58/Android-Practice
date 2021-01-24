package com.example.week4

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.example.week4.GameView.Companion.screenRatioX
import com.example.week4.GameView.Companion.screenRatioY

class Ufo(res: Resources) {
    var wasShot = true
    var speed = 20
    var x = 0
    var y: Int? = null
    var width: Int? = null
    var height: Int? = null
    var birdCounter = 1

    lateinit var ufo: Bitmap

    init{
        ufo = BitmapFactory.decodeResource(res, R.drawable.ufo)

        width = ufo.width
        height = ufo.height

        width = width!! / 3
        height = height!! / 3

        width = width!! * screenRatioX!!.toInt()
        height = height!! * screenRatioY!!.toInt()

        ufo = Bitmap.createScaledBitmap(ufo, width!!, height!!, false)

        y = -height!!
    }

    @JvmName("getUfo1")
    fun getUfo(): Bitmap{
        if (birdCounter == 1){
            birdCounter++
            return ufo
        }
        if (birdCounter == 2){
            birdCounter++
            return ufo
        }
        if (birdCounter == 3){
            birdCounter++
            return ufo
        }

        birdCounter = 1
        return ufo
    }

    fun getCollisionShape(): Rect {
        return Rect(x, y!!, x + width!!, y!! + height!!)
    }
}