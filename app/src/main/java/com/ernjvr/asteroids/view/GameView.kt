package com.ernjvr.asteroids.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.ernjvr.asteroids.graphics.Shape
import com.ernjvr.asteroids.model.AsteroidFactory

class GameView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val asteroids = AsteroidFactory().asteroids

    private lateinit var customBitmap: Bitmap
    private lateinit var customCanvas: Canvas
    var paintColor = Color.BLACK
    var shape = Shape.CIRCLE
    private var radius = 30F


    init {
        setBackgroundColor(Color.LTGRAY)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawBitmap(customBitmap, 0F, 0F, paint)

        asteroids.forEach {
            paint.color = it.color
            canvas?.drawCircle(it.xx, it.yy, it.radius, paint)
            it.move(width, height)
        }
        invalidate()
    }

    private fun calcRadius(percent: Int): Float {
        return (width / 100 * percent).toFloat()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        paint.color = paintColor
        paint.style = Paint.Style.FILL
        var touchX = event?.x ?: 0F
        var touchY = event?.y ?: 0F
        println("touchX $touchX, touchY $touchY, width $width, height $height")
        if (touchY > height) touchY = height.toFloat() - (radius * 2)
        if (touchY - radius < 0) touchY = 0 + radius
        if ((touchX + radius) >= width.toFloat()) touchX -= radius
        if ((touchX - radius) <= 0F) touchX += radius
        println("$touchX + $radius = " + (touchX + radius))
        println("$touchX - $radius = " + (touchX - radius))
        println("new touchX $touchX, touchY $touchY, width $width, height $height")
        customCanvas.drawColor(Color.WHITE)
        when (shape) {
            Shape.RECTANGLE -> customCanvas.drawRect(getRect(touchX.toInt(), touchY.toInt()), paint)
            else -> customCanvas.drawCircle(touchX, touchY, radius, paint)
        }
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        customBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        customCanvas = Canvas(customBitmap)
        radius = calcRadius(3)
        asteroids.forEachIndexed { index, asteroid ->
            val factor = index + 1
            asteroid.radius = radius
            asteroid.xxx = factor * (radius / 3)
            asteroid.yyy = factor * (radius / 3)
        }
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private fun getRect(x: Int, y: Int): Rect {
        val rectSize = (radius * 2).toInt()
        return Rect(x, y, (x + rectSize), (y + rectSize))
    }

    companion object {
        private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    }
}