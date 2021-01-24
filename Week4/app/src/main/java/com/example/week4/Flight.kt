package com.example.week4

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.example.week4.GameView.Companion.screenRatioX
import com.example.week4.GameView.Companion.screenRatioY

class Flight(gameView: GameView, screenY: Int, res: Resources) {

    var x: Int? = null
    var y: Int? = null
    var wingCounter = 0
    var width: Int? = null
    var height: Int? = null
    lateinit var flight: Bitmap
    lateinit var shoot1: Bitmap
    lateinit var dead: Bitmap
    var isGoingUp = false
    var toShoot = 0
    var shootCounter = 1
    lateinit var gameView: GameView

    init{
        this.gameView = gameView
        flight = BitmapFactory.decodeResource(res, R.drawable.jet)

        width = flight.width
        height = flight.height

        width  = width!!/12
        height = height!!/12

        width = width!! * (screenRatioX!!.toInt())
        height = height!! * (screenRatioY!!.toInt())

        flight = Bitmap.createScaledBitmap(flight, width!!, height!!, false)
        shoot1 = BitmapFactory.decodeResource(res, R.drawable.jet)

        shoot1 = Bitmap.createScaledBitmap(shoot1, width!!, height!!, false)

        dead = BitmapFactory.decodeResource(res, R.drawable.jet)
        dead = Bitmap.createScaledBitmap(dead, width!!, height!!, false)

        y = screenY / 2
        x = 64 * screenRatioX!!.toInt()

    }

    @JvmName("getFlight1")
    fun getFlight(): Bitmap {
        if(toShoot != 0){
            if(shootCounter == 1){
                shootCounter++
                return shoot1
            }
            if(shootCounter == 2){
                shootCounter++
                return shoot1
            }
            if(shootCounter == 3){
                shootCounter++
                return shoot1
            }
            if(shootCounter == 4){
                shootCounter++
                return shoot1
            }

            shootCounter = 1
            toShoot--
            gameView.newBullet()

            return shoot1
        }
        if(wingCounter == 0){
            wingCounter++
            return flight
        }

        wingCounter--
        return flight

    }

    fun getCollisionShape(): Rect {
        return Rect(x!!, y!!, x!! + (width!!)/2, y!! + (height!!/2))
    }

    fun getDeadFlight():Bitmap{
        return dead
    }
}