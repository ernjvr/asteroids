package com.ernjvr.asteroids.controller

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.graphics.Rect
import android.view.MotionEvent
import android.widget.TextView
import android.widget.Toast
import com.ernjvr.asteroids.GameActivity
import com.ernjvr.asteroids.GameOverActivity
import com.ernjvr.asteroids.R
import com.ernjvr.asteroids.graphics.Shape
import com.ernjvr.asteroids.valitation.GameValidator
import com.ernjvr.asteroids.view.GameView

class GameController(private val activity: GameActivity, private val view: GameView) : ViewController {

    private var lives = 3
    private var score = 0

    override fun receiveTouch(event: MotionEvent?) {
        val touchX = constrainTouchXToGameSurface(event?.x ?: 0F)
        val touchY = constrainTouchYToGameSurface(event?.y ?: 0F)

        if (GameValidator.isCollided(touchX, touchY, view.radius, view.asteroids)) {
            println("isCollided true")
            lives--
            displayLives()
            when (view.shape) {
                Shape.RECTANGLE -> view.customCanvas.drawRect(getRect(touchX.toInt(), touchY.toInt()), GameView.paint)
                else -> {
                    view.customCanvas.drawCircle(touchX, touchY, view.radius * 4, GameView.paint)
                    Thread.sleep(50)
                    view.customCanvas.drawCircle(touchX, touchY, view.radius / 3, GameView.paint)

                }
            }
            Toast.makeText(activity, "Crash!", Toast.LENGTH_LONG).show()

            if (lives == 0) {
                handleGameOver()
            }
        } else {
            score++
            displayScore()
            when (view.shape) {
                Shape.RECTANGLE -> view.customCanvas.drawRect(getRect(touchX.toInt(), touchY.toInt()), GameView.paint)
                else -> view.customCanvas.drawCircle(touchX, touchY, view.radius, GameView.paint)
            }
        }

    }

    private fun handleGameOver() {
        Toast.makeText(activity, "Game Over!", Toast.LENGTH_LONG).show()
        val intent = Intent(activity, GameOverActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("score", score)
        activity.startActivity(intent)
        activity.finish()
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
        tvLives?.text = String.format(Integer.toString(lives))
    }

    private fun displayScore() {
        val tvScore = activity.findViewById<TextView>(R.id.tvScore)
        tvScore?.text = String.format(Integer.toString(score))
    }

    private fun getRect(x: Int, y: Int): Rect {
        val rectSize = (view.radius * 2).toInt()
        return Rect(x, y, (x + rectSize), (y + rectSize))
    }
}