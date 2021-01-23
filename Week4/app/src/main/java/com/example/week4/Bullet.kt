package com.example.week4

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.example.week4.GameView.Companion.screenRatioX
import com.example.week4.GameView.Companion.screenRatioY

class Bullet(res: Resources) {

    var x:Int? = null
    var y: Int? = null
    lateinit var bullet: Bitmap
    var width: Int
    var height: Int

    init{
        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet)

        width = bullet.width
        height = bullet.height

        width /= 4
        height /= 4

        width *= screenRatioX!!.toInt()
        height *= screenRatioY!!.toInt()

        bullet = Bitmap.createScaledBitmap(bullet, width, height, false)
    }

    fun getCollisionShape(): Rect {
        return Rect(x!!, y!!, x!! + width, y!! + height)
    }
}