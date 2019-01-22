package com.ernjvr.asteroids.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.ernjvr.asteroids.GameActivity
import com.ernjvr.asteroids.controller.GameController
import com.ernjvr.asteroids.graphics.Shape
import com.ernjvr.asteroids.model.AsteroidFactory

class GameView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val asteroids = AsteroidFactory().asteroids
    private val gameController: GameController
    private lateinit var customBitmap: Bitmap
    lateinit var customCanvas: Canvas
    var paintColor = Color.BLACK
    var shape = Shape.CIRCLE
    var radius = 30F


    init {
        setBackgroundColor(Color.LTGRAY)
        val activity = (context as ActivityProvider).currentActivity() as GameActivity
        gameController = GameController(activity, this)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawBitmap(customBitmap, 0F, 0F, paint)

        asteroids.forEach {
            paint.color = it.color
            canvas?.drawCircle(it.x, it.y, it.radius, paint)
            it.move(width, height)
        }
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        paint.color = paintColor
        paint.style = Paint.Style.FILL
        customCanvas.drawColor(Color.LTGRAY)
        gameController.receiveTouch(event)
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        customBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        customCanvas = Canvas(customBitmap)
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
            asteroid.radius = radius
            asteroid.velocityX = factor * (radius / 3)
            asteroid.velocityY = factor * (radius / 3)
        }
    }

    companion object {
        val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        const val SCALE_PERCENTAGE = 3
    }
}