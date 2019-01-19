package com.ernjvr.asteroids.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.ernjvr.asteroids.graphics.Shape
import com.ernjvr.asteroids.model.AsteroidFactory

class GameView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val asteroids = AsteroidFactory().asteroids

    private lateinit var customBitmap: Bitmap
    private lateinit var customCanvas: Canvas
    var paintColor = Color.BLACK
    var shape = Shape.CIRCLE


    init {
        setBackgroundColor(Color.LTGRAY)
    }

    override fun onDraw(canvas: Canvas?) {
        println("drawing")
        canvas?.drawBitmap(customBitmap, 0F, 0F, paint)

        asteroids.forEach {
            paint.color = it.color
            canvas?.drawCircle(it.xx, it.yy, it.RADIUS, paint)
            it.moveBall(width, height)
        }
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        paint.color = paintColor
        paint.style = Paint.Style.FILL
        var touchX = event?.x ?: 0F
        var touchY = event?.y ?: 0F
        println("touchX $touchX, touchY $touchY, width $width, height $height")
        if (touchY > height) touchY = height.toFloat() - (RADIUS * 2)
        if (touchY - RADIUS < 0) touchY = 0 + RADIUS
        if ((touchX + RADIUS) >= width.toFloat()) touchX -= RADIUS
        if ((touchX - RADIUS) <= 0F) touchX += RADIUS
        println("$touchX + $RADIUS = " + (touchX + RADIUS))
        println("$touchX - $RADIUS = " + (touchX - RADIUS))
        println("new touchX $touchX, touchY $touchY, width $width, height $height")
        customCanvas.drawColor(Color.WHITE)
        when(shape) {
            Shape.RECTANGLE -> customCanvas.drawRect(getRect(touchX.toInt(), touchY.toInt()), paint)
            else -> customCanvas.drawCircle(touchX, touchY, RADIUS, paint)
        }
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        customBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        customCanvas = Canvas(customBitmap)
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private fun getRect(x: Int, y: Int): Rect {
        val rectSize = (RADIUS * 2).toInt()
        return Rect(x, y, (x + rectSize), (y + rectSize))
    }

    companion object {
        private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        const val RADIUS = 30F
    }
}