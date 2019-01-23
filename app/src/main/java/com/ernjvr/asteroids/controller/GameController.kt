package com.ernjvr.asteroids.controller

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.view.MotionEvent
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
            lives--
            displayLives()
            when (view.shape) {
                Shape.RECTANGLE -> view.shape = Shape.CIRCLE
                else -> {
                    updateSpaceShip(touchX, touchY, view.radius * 4)
                    Thread.sleep(50)
                }
            }
            Toast.makeText(activity, activity.getString(R.string.crash), Toast.LENGTH_SHORT).show()

            if (lives == 0) {
                handleGameOver()
            }
        } else {
            score++
            displayScore()
            when (view.shape) {
                Shape.RECTANGLE -> view.shape = Shape.CIRCLE
                else -> updateSpaceShip(touchX, touchY, view.radius)
            }
        }
    }

    private fun updateSpaceShip(x: Float, y: Float, radius: Float) {
        view.spaceShip.x = x
        view.spaceShip.y = y
        view.spaceShip.radius = radius
    }

    private fun handleGameOver() {
        val intent = Intent(activity, GameOverActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(activity.getString(R.string.score), score)
        activity.startActivity(intent)
        activity.finish()
    }

    private fun constrainTouchXToGameSurface(touchX: Float): Float {
        var x = touchX
        if ((x + view.radius) >= view.width.toFloat()) x -= view.radius
        if ((x - view.radius) <= 0) x += view.radius
        return x
    }

    private fun constrainTouchYToGameSurface(touchY: Float): Float {
        var y = touchY
        if (y + view.radius > view.height) y = view.height.toFloat() - view.radius
        if (y - view.radius < 0) y = view.radius
        return y
    }

    private fun displayLives() {
        activity.tvLives.text = String.format(Integer.toString(lives))
    }

    private fun displayScore() {
        activity.tvScore.text = String.format(Integer.toString(score))
    }
}