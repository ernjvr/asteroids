package com.ernjvr.asteroids.model

import android.graphics.Color

class AsteroidFactory {

    private val xs = listOf(40F, 65F, 90F)
    private val ys = listOf(40F, 65F, 90F)
    private val xxs = listOf(10F, 12F, 15F)
    private val yys = listOf(10F, 12F, 15F)
    private val colors = listOf(Color.RED, Color.YELLOW, Color.GREEN)
    val asteroids = listOf(Asteroid(), Asteroid(radius = 30F), Asteroid(radius = 40F))

    init {
        asteroids.forEachIndexed { index, asteroid ->
            asteroid.color = colors[index]
            asteroid.x = xs[index]
            asteroid.y = ys[index]
            asteroid.velocityX = xxs[index]
            asteroid.velocityY = yys[index]
        }
    }
}