package com.ernjvr.asteroids.controller

import android.graphics.Rect
import android.view.MotionEvent
import android.widget.TextView
import com.ernjvr.asteroids.MainActivity
import com.ernjvr.asteroids.R
import com.ernjvr.asteroids.graphics.Shape
import com.ernjvr.asteroids.valitation.GameValidator
import com.ernjvr.asteroids.view.GameView

class GameController(private val activity: MainActivity, private val view: GameView) : ViewController {

    private var lives = 3

    override fun receiveTouch(event: MotionEvent?) {
        val touchX = constrainTouchXToGameSurface(event?.x ?: 0F)
        val touchY = constrainTouchYToGameSurface(event?.y ?: 0F)

        if (GameValidator.isCollided(touchX, touchY, view.radius, view.asteroids)) {
            println("isCollided true")
            lives--
            displayLives()

            if (lives == 0) {
                handleGameOver()
            }
        }
        when (view.shape) {
            Shape.RECTANGLE -> view.customCanvas.drawRect(getRect(touchX.toInt(), touchY.toInt()), GameView.paint)
            else -> view.customCanvas.drawCircle(touchX, touchY, view.radius, GameView.paint)
        }
    }

    private fun handleGameOver() {
        println("Game Over!")
        //display toast - game over
        // move to start screen
    }

    private fun constrainTouchXToGameSurface(touchX: Float): Float {
        var touchX1 = touchX
        if ((touchX1 + view.radius) >= view.width.toFloat()) touchX1 -= view.radius
        if ((touchX1 - view.radius) <= 0F) touchX1 += view.radius
        return touchX1
    }

    private fun constrainTouchYToGameSurface(touchY: Float): Float {
        var touchY1 = touchY
        if (touchY1 + view.radius > view.height) touchY1 = view.height.toFloat() - view.radius
        if (touchY1 - view.radius < 0) touchY1 = view.radius
        return touchY1
    }

    private fun displayLives() {
        val tvLives = activity.findViewById<TextView>(R.id.tvLives)
        println("tvLives $tvLives")
        tvLives?.text = String.format(Integer.toString(lives))
    }

    private fun getRect(x: Int, y: Int): Rect {
        val rectSize = (view.radius * 2).toInt()
        return Rect(x, y, (x + rectSize), (y + rectSize))
    }
}