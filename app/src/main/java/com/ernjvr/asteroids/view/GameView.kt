package com.ernjvr.asteroids.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.ernjvr.asteroids.GameActivity
import com.ernjvr.asteroids.controller.GameController
import com.ernjvr.asteroids.controller.ViewController
import com.ernjvr.asteroids.data.service.DataService
import com.ernjvr.asteroids.engine.GameThread
import com.ernjvr.asteroids.model.Game
import kotlin.random.Random

class GameView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    private val gameController: ViewController
    private val gameThread: GameThread
    private val game = Game()

    init {
        holder.addCallback(this)
        gameThread = GameThread(holder, this)
        setRandomBackgroundImage()
        val activity = (context as ActivityProvider).currentActivity() as GameActivity
        gameController = GameController(activity, this, game)
        focusable = View.FOCUSABLE
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        gameController.update()

        paint.color = game.spaceShip.color
        canvas?.drawCircle(game.spaceShip.x, game.spaceShip.y, game.spaceShip.radius, paint)

        game.asteroids.forEach {
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
        gameController.receiveSizeChanged(w, h, oldw, oldh)
        super.onSizeChanged(w, h, oldw, oldh)
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

    fun setRandomBackgroundImage() {
        setBackgroundResource(
            DataService.backgroundImageMap.getValue(
                Random.nextInt(1, DataService.backgroundImageMap.size + 1))
        )
    }

    companion object {
        val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    }
}