package com.ernjvr.asteroids.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.ernjvr.asteroids.GameActivity
import com.ernjvr.asteroids.controller.GameController
import com.ernjvr.asteroids.engine.GameThread
import com.ernjvr.asteroids.image.ImageFactory
import com.ernjvr.asteroids.model.Asteroid
import com.ernjvr.asteroids.model.AsteroidFactory
import com.ernjvr.asteroids.model.SpaceShip
import kotlin.random.Random

class GameView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    val asteroids = AsteroidFactory().asteroids
    private val gameController: GameController
    private val gameThread: GameThread
    var radius = 0F
    private val spaceShip = SpaceShip(0F, 0F, 0F, Color.WHITE)

    init {
        holder.addCallback(this)
        gameThread = GameThread(holder, this)
        setRandomBackgroundImage()
        val activity = (context as ActivityProvider).currentActivity() as GameActivity
        gameController = GameController(activity, this)
        focusable = View.FOCUSABLE
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

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
        gameController.receiveTouch(event)
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        scaleRadius()
        scaleAsteroids()
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private fun scaleRadius() {
        radius = (width / 100 * SCALE_PERCENTAGE).toFloat()
    }

    private fun scaleAsteroids() {
        asteroids.forEachIndexed { index, asteroid ->
            scaleAsteroid(asteroid, index + 1)
        }
    }

    private fun scaleAsteroid(asteroid: Asteroid, factor: Int) {
        val velocity = factor * (radius / VELOCITY_PERCENTAGE)
        asteroid.radius = radius
        asteroid.velocityX = velocity
        asteroid.velocityY = velocity
    }

    fun addAsteroid() {
        val asteroid = Asteroid(Color.RED, 0F, 0F, 5F, 5F)
        scaleAsteroid(asteroid, 1)
        asteroids.add(asteroid)
    }

    fun updateSpaceShip(x: Float, y: Float, radius: Float) {
        spaceShip.x = x
        spaceShip.y = y
        spaceShip.radius = radius
    }

    fun setRandomBackgroundImage() {
        setBackgroundResource(
            ImageFactory.backgroundImageMap.getValue(Random.nextInt(1, ImageFactory.backgroundImageMap.size))
        )
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        gameThread.running = true
        gameThread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        // surface changed
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        gameThread.running = false
    }

    companion object {
        val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        const val SCALE_PERCENTAGE = 3
        const val VELOCITY_PERCENTAGE = 3
    }
}