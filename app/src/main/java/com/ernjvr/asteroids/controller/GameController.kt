package com.ernjvr.asteroids.controller

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.view.MotionEvent
import android.widget.Toast
import com.ernjvr.asteroids.GameActivity
import com.ernjvr.asteroids.GameOverActivity
import com.ernjvr.asteroids.R
import com.ernjvr.asteroids.model.Game
import com.ernjvr.asteroids.sound.AudioPlayer
import com.ernjvr.asteroids.valitation.GameValidator
import com.ernjvr.asteroids.view.GameView

class GameController(private val activity: GameActivity, private val view: GameView, private val game: Game) : ViewController {
    
    private var explode = false
    private var ambiencePlaying = 0
    private val audioPlayer = AudioPlayer(activity)
    private val gameValidator = GameValidator(game.asteroids)

    override fun receiveTouch(event: MotionEvent?) {
        handleAmbience()

        val touchX = constrainTouchXToGameSurface(event?.x ?: 0F)
        val touchY = constrainTouchYToGameSurface(event?.y ?: 0F)
        game.updateSpaceShip(touchX, touchY, game.radius)

        if (!gameValidator.isCollided(touchX, touchY, game.radius)) {
            handleSuccessfulMove()
        }
    }

    override fun update() {
        checkCollision(game.spaceShip.x, game.spaceShip.y)
    }

    private fun checkCollision(x: Float, y: Float) {
        when {
            explode -> {
                game.updateSpaceShip(-100F, -100F, game.radius)
                explode = false
            }
            gameValidator.isCollided(x, y, game.radius) -> {
                explode = true
                handleCollision(x, y)
            }
            else -> game.updateSpaceShip(x, y, game.radius)
        }
    }

    private fun handleAmbience() {
        if (ambiencePlaying == 0) {
            audioPlayer.playAudio(AudioPlayer.AMBIENCE, -1)
            ambiencePlaying = 1
        }
    }

    private fun handleCollision(touchX: Float, touchY: Float) {
        game.lives--
        displayLives()
        audioPlayer.playAudio(AudioPlayer.EXPLOSION, 0, 2)

        if (explode) {
            game.updateSpaceShip(touchX, touchY, game.radius * 4)
            Thread.sleep(50)
        }
        Toast.makeText(activity, activity.getString(R.string.crash), Toast.LENGTH_SHORT).show()

        if (game.lives == 0) {
            handleGameOver()
        } else {
            view.setRandomBackgroundImage()
        }
    }

    private fun handleSuccessfulMove() {
        game.score++
        displayScore()

        if (game.score % 500 == 0) {
            game.addAsteroid()
        }
    }

    private fun handleGameOver() {
        val intent = Intent(activity, GameOverActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(activity.getString(R.string.score), game.score)
        activity.startActivity(intent)
        activity.finish()
    }

    private fun constrainTouchXToGameSurface(touchX: Float): Float {
        var x = touchX
        if ((x + game.radius) >= view.width.toFloat()) x -= game.radius
        if ((x - game.radius) <= 0) x += game.radius
        return x
    }

    private fun constrainTouchYToGameSurface(touchY: Float): Float {
        var y = touchY
        if (y + game.radius > view.height) y = view.height.toFloat() - game.radius
        if (y - game.radius < 0) y = game.radius
        return y
    }

    private fun displayLives() {
        activity.tvLives.text = String.format(Integer.toString(game.lives))
    }

    private fun displayScore() {
        activity.tvScore.text = String.format(Integer.toString(game.score))
    }
}