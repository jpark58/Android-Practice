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
    lateinit var flight1: Bitmap
    lateinit var flight2: Bitmap
    lateinit var shoot1: Bitmap
    lateinit var shoot2: Bitmap
    lateinit var shoot3: Bitmap
    lateinit var shoot4: Bitmap
    lateinit var shoot5: Bitmap
    lateinit var dead: Bitmap
    var isGoingUp = false
    var toShoot = 0
    var shootCounter = 1
    lateinit var gameView: GameView

    init{
        this.gameView = gameView
        flight1 = BitmapFactory.decodeResource(res, R.drawable.jet)
        //flight2 = BitmapFactory.decodeResource(res, R.drawable.fly2)

        width = flight1.width
        height = flight1.height

        width  = width!!/12
        height = height!!/12

        width = width!! * (screenRatioX!!.toInt())
        height = height!! * (screenRatioY!!.toInt())

        flight1 = Bitmap.createScaledBitmap(flight1, width!!, height!!, false)
       // flight2 = Bitmap.createScaledBitmap(flight2, width!!, height!!, false)

        shoot1 = BitmapFactory.decodeResource(res, R.drawable.jet)
        //shoot2 = BitmapFactory.decodeResource(res, R.drawable.shoot2)
        //shoot3 = BitmapFactory.decodeResource(res, R.drawable.shoot3)
        //shoot4 = BitmapFactory.decodeResource(res, R.drawable.shoot4)
        //shoot5 = BitmapFactory.decodeResource(res, R.drawable.shoot5)

        shoot1 = Bitmap.createScaledBitmap(shoot1, width!!, height!!, false)
        //shoot2 = Bitmap.createScaledBitmap(shoot2, width!!, height!!, false)
        //shoot3 = Bitmap.createScaledBitmap(shoot3, width!!, height!!, false)
        //shoot4 = Bitmap.createScaledBitmap(shoot4, width!!, height!!, false)
        //shoot5 = Bitmap.createScaledBitmap(shoot5, width!!, height!!, false)

        dead = BitmapFactory.decodeResource(res, R.drawable.jet)
        dead = Bitmap.createScaledBitmap(dead, width!!, height!!, false)

        y = screenY / 2
        x = 64 * screenRatioX!!.toInt()

    }

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
            return flight1
        }

        wingCounter--
        return flight1

    }

    fun getCollisionShape(): Rect {
        return Rect(x!!, y!!, x!! + (width!!)/2, y!! + (height!!/2))
    }

    fun getDeadFlight():Bitmap{
        return dead
    }
}