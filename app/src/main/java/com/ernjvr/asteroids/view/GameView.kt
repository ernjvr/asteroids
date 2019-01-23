package com.ernjvr.asteroids.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.ernjvr.asteroids.GameActivity
import com.ernjvr.asteroids.R
import com.ernjvr.asteroids.controller.GameController
import com.ernjvr.asteroids.engine.GameThread
import com.ernjvr.asteroids.graphics.Shape
import com.ernjvr.asteroids.model.AsteroidFactory
import com.ernjvr.asteroids.model.SpaceShip

class GameView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    val asteroids = AsteroidFactory().asteroids
    private val gameController: GameController
    private val gameThread: GameThread
    private lateinit var customBitmap: Bitmap
    var shape = Shape.CIRCLE
    var radius = 0F
    val spaceShip = SpaceShip(0F, 0F, 0F, Color.WHITE)


    init {
        holder.addCallback(this)
        gameThread = GameThread(holder, this)
//        setBackgroundColor(Color.LTGRAY)
        setBackgroundResource(R.drawable.space2)
        val activity = (context as ActivityProvider).currentActivity() as GameActivity
        gameController = GameController(activity, this)
        focusable = View.FOCUSABLE
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.drawBitmap(customBitmap, 0F, 0F, paint)

        paint.color = spaceShip.color
        canvas?.drawCircle(spaceShip.x, spaceShip.y, spaceShip.radius, paint)

        asteroids.forEach {
            paint.color = it.color
            canvas?.drawCircle(it.x, it.y, it.radius, paint)
            it.move(width, height)
        }
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        gameController.receiveTouch(event)
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        customBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        scaleRadius()
        scaleAsteroids()
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private fun scaleRadius() {
        radius = (width / 100 * SCALE_PERCENTAGE).toFloat()
    }

    private fun scaleAsteroids() {
        asteroids.forEachIndexed { index, asteroid ->
            val factor = index + 1
            val velocity = factor * (radius / VELOCITY_PERCENTAGE)
            asteroid.radius = radius
            asteroid.velocityX = velocity
            asteroid.velocityY = velocity
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        gameThread.running = true
        gameThread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        // surface changed
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        while (true) {
            gameThread.running = false
            gameThread.join()
            break
        }
    }

    companion object {
        val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        const val SCALE_PERCENTAGE = 3
        const val VELOCITY_PERCENTAGE = 3
    }
}