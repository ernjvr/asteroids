package com.ernjvr.asteroids.model

import android.graphics.Color
import com.ernjvr.asteroids.model.factory.AsteroidFactory

class Game {

    val asteroids = AsteroidFactory().asteroids
    val spaceShip = SpaceShip(-100F, -100F, 0F, Color.WHITE)
    var radius = 0F
    var lives = 3
    var score = 0

    fun scaleRadius(width: Int) {
        radius = (width / 100 * SCALE_PERCENTAGE).toFloat()
    }

    fun scaleAsteroids() {
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

    companion object {
        const val SCALE_PERCENTAGE = 3
        const val VELOCITY_PERCENTAGE = 3
    }
}