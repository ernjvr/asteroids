package com.ernjvr.asteroids.controller

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.view.MotionEvent
import android.widget.Toast
import com.ernjvr.asteroids.GameActivity
import com.ernjvr.asteroids.GameOverActivity
import com.ernjvr.asteroids.R
import com.ernjvr.asteroids.sound.AudioPlayer
import com.ernjvr.asteroids.valitation.GameValidator
import com.ernjvr.asteroids.view.GameView

class GameController(private val activity: GameActivity, private val view: GameView) : ViewController {

    private var lives = 3
    private var score = 0
    private var ambiencePlaying = 0
    private val audioPlayer = AudioPlayer(activity)
    private val gameValidator = GameValidator(view.asteroids)

    override fun receiveTouch(event: MotionEvent?) {
        handleAmbience()
        val touchX = constrainTouchXToGameSurface(event?.x ?: 0F)
        val touchY = constrainTouchYToGameSurface(event?.y ?: 0F)

        if (gameValidator.isCollided(touchX, touchY, view.radius)) {
            handleCollision(touchX, touchY)
        } else {
            handleSuccessfulMove(touchX, touchY)
        }
    }

    private fun handleAmbience() {
        if (ambiencePlaying == 0) {
            audioPlayer.playAudio(AudioPlayer.AMBIENCE, -1)
            ambiencePlaying = 1
        }
    }

    private fun handleCollision(touchX: Float, touchY: Float) {
        lives--
        displayLives()
        audioPlayer.playAudio(AudioPlayer.EXPLOSION, 0, 2)
        view.updateSpaceShip(touchX, touchY, view.radius * 4)
        Thread.sleep(50)
        Toast.makeText(activity, activity.getString(R.string.crash), Toast.LENGTH_SHORT).show()

        if (lives == 0) {
            handleGameOver()
        } else {
            view.setRandomBackgroundImage()
        }
    }

    private fun handleSuccessfulMove(touchX: Float, touchY: Float) {
        score++
        displayScore()
        view.updateSpaceShip(touchX, touchY, view.radius)
        if (score % 500 == 0) {
            view.addAsteroid()
        }
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