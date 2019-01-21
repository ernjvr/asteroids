package com.ernjvr.asteroids.valitation

import com.ernjvr.asteroids.model.Asteroid

class GameValidator {

    companion object {
        fun isCollided(touchX: Float, touchY: Float, radius: Float, asteroids: List<Asteroid>): Boolean {
            val leftSideOfPlayer = touchX - radius
            val topSideOfPlayer = touchY - radius
            val playerLength = radius * 2

            asteroids.forEach {
                val leftSideOfAsteroid = it.x - it.radius
                val topSideOfAsteroid = it.y - it.radius
                val asteroidLength = it.radius * 2

                if (
                    (leftSideOfPlayer >= leftSideOfAsteroid && leftSideOfPlayer <= leftSideOfAsteroid + asteroidLength
                            && topSideOfPlayer >= topSideOfAsteroid && topSideOfPlayer <= topSideOfAsteroid + asteroidLength) ||
                    (leftSideOfAsteroid >= leftSideOfPlayer && leftSideOfAsteroid <= leftSideOfPlayer + playerLength
                            && topSideOfAsteroid >= topSideOfPlayer && topSideOfAsteroid <= topSideOfPlayer + playerLength)
                ) {
                    return true
                }
            }
            return false
        }
    }
}