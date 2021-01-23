package com.example.week4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.*
import android.media.SoundPool
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.*
import kotlin.collections.ArrayList

class GameView(activity: GameActivity, screenX: Int, screenY: Int): SurfaceView(activity), SurfaceHolder.Callback{

    private lateinit var thread: Thread
    private var isPlaying: Boolean? =null
    private var isGameOver = false
    private lateinit var background1: Background
    private lateinit var background2: Background
    private var screenX: Int? = null
    private var screenY: Int? = null
    private lateinit var ourHolder: SurfaceHolder
    private val painter: Paint
    private lateinit var flight: Flight
    var bullets: ArrayList<Bullet>? = null
    private lateinit var birds: ArrayList<Bird>
    private lateinit var random: Random
    private var score = 0
    private lateinit var prefs: SharedPreferences
    private lateinit var activity: GameActivity

    companion object{
        var screenRatioX: Float? = null
        var screenRatioY: Float? = null
    }


    init{
        this.activity = activity
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE)
        this.screenX = screenX
        this.screenY = screenY

        screenRatioX = 1184f / screenX
        screenRatioY = 768f / screenY

        this.background1 = Background(screenX, screenY, resources)
        this.background2 = Background(screenX, screenY, resources)

        this.background2.x = screenX
        Log.d("로그2", "1: ${background1.x} // 2: ${background2.x}")

        flight = Flight(this, screenY, resources)

        this.ourHolder = holder
        this.ourHolder.addCallback(this)

        painter = Paint()
        painter.textSize = 128f
        painter.color = Color.WHITE

        bullets = ArrayList<Bullet>()
        birds = ArrayList<Bird>()

        for(i in 0..3){
            var bird = Bird(resources)
            birds.add(bird)
        }

        random = Random()
    }
    override fun surfaceCreated(holder: SurfaceHolder) {
        isPlaying = true
        thread = Thread{
            while(isPlaying!!){
                update()
                if(ourHolder.surface.isValid){
                    Log.d("로그", "In GameView draw")
                    val canvas = ourHolder.lockCanvas()
                    canvas.drawBitmap(background1.background, background1.x.toFloat(), background1.y.toFloat(), painter)
                    canvas.drawBitmap(background2.background, background2.x.toFloat(), background2.y.toFloat(), painter)

                    for(bird in birds){
                        canvas.drawBitmap(bird.getBird(), bird.x.toFloat(), bird.y!!.toFloat(), painter)
                    }

                    canvas.drawText(score.toString(), screenX!! / 2f, 164f, painter)

                    if(isGameOver){
                        isPlaying = false
                        canvas.drawBitmap(flight.getDeadFlight(), flight.x!!.toFloat(), flight.y!!.toFloat(), painter)
                        ourHolder.unlockCanvasAndPost(canvas)
                        saveIfHighScore()
                        waitBeforeExiting()

                    }else{
                        canvas.drawBitmap(flight.getFlight(), flight.x!!.toFloat(), flight.y!!.toFloat(), painter)

                        for(bullet in bullets!!){
                            canvas.drawBitmap(bullet.bullet, bullet.x!!.toFloat(), bullet.y!!.toFloat(), painter)
                        }

                        ourHolder.unlockCanvasAndPost(canvas)
                    }
                }

                Thread.sleep(34)
            }
        }
        Log.d("로그2", "In GameView surfaceCreated")
        thread.start()
    }

    private fun waitBeforeExiting() {
        Thread.sleep(3000)
        activity.startActivity(Intent(activity, MainActivity::class.java))
        activity.finish()
    }

    private fun saveIfHighScore() {
        if(prefs.getInt("highscore", 0) < score){
            var editor = prefs.edit()
            editor.putInt("highscore", score)
            editor.apply()
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        thread.join()
    }


    fun pause(){
        isPlaying=false
        thread.join()
    }

    fun update(){
        background1.x = background1.x - (10 * (screenRatioX!!.toInt()))
        background2.x = background2.x - (10 * (screenRatioX!!.toInt()))

        if(background1.x + background1.background.width < 0){
            background1.x = screenX!!
        }

        if(background2.x + background2.background.width < 0){
            background2.x = screenX!!
        }

        if(flight.isGoingUp){
            flight.y = flight.y?.minus(30 * screenRatioY!!.toInt())
        }else{
            flight.y = flight.y?.plus(30 * screenRatioY!!.toInt())
        }

        if(flight.y!! < 0){
            flight.y = 0
        }

        if(flight.y!! >= screenY!! - flight.height!!){
            flight.y = screenY!! - flight.height!!
        }

        var trash = ArrayList<Bullet>()

        for (bullet in bullets!!){
            if(bullet.x!! > screenX!!){
                trash.add(bullet)
            }

            bullet.x = bullet.x!! + (50 * screenRatioX!!.toInt())

            for(bird in birds){
                if(Rect.intersects(bird.getCollisionShape(), bullet.getCollisionShape())){
                    score++
                    bird.x = -500
                    bullet.x = screenX!! + 500
                    bird.wasShot = true
                }
            }
        }

        for (bullet in trash){
            bullets!!.remove(bullet)
        }

        for(bird in birds){
            bird.x -= bird.speed

            if(bird.x + bird.width!! < 0){

                if(!bird.wasShot){
                    isGameOver = true
                    return
                }
                var bound = 30 * screenRatioX!!.toInt()
                bird.speed = random.nextInt(bound)

                if(bird.speed < 10 * screenRatioX!!.toInt()){
                    bird.speed = 10 * screenRatioX!!.toInt()
                }

                bird.x = screenX!!
                bird.y = random.nextInt(screenY!! - bird.height!!)

                bird.wasShot = false
            }

            if(Rect.intersects(bird.getCollisionShape(), flight.getCollisionShape())){
                isGameOver = true
                return
            }
        }

        Log.d("로그", "In GameView update ${background1.x} and ${background2.x}")
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event != null) {
            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    if(event.getX() < screenX!! / 2){
                        flight.isGoingUp = true
                    }
                }
                MotionEvent.ACTION_UP -> {
                    flight.isGoingUp = false
                    if(event.getX() > screenX!! / 2){
                        flight.toShoot++
                    }
                }
            }
        }

        return true
    }

    fun newBullet() {
        var bullet = Bullet(resources)
        bullet.x = flight.x?.plus(flight.width!!)
        bullet.y = flight.y?.plus((flight.height?.div(2)!!))

        bullets!!.add(bullet)
    }

}