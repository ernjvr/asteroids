package com.ernjvr.asteroids.model.factory

import android.graphics.Color
import com.ernjvr.asteroids.model.Asteroid

class AsteroidFactory {

    private val xs = listOf(40F, 65F, 90F)
    private val ys = listOf(40F, 65F, 90F)
    private val velocityXs = listOf(10F, 12F, 15F)
    private val velocityYs = listOf(10F, 12F, 15F)
    private val colors = listOf(Color.RED, Color.YELLOW, Color.GREEN)

    val asteroids = mutableListOf(
        Asteroid(),
        Asteroid(),
        Asteroid()
    )

    init {
        asteroids.forEachIndexed { index, asteroid ->
            asteroid.color = colors[index]
            asteroid.x = xs[index]
            asteroid.y = ys[index]
            asteroid.velocityX = velocityXs[index]
            asteroid.velocityY = velocityYs[index]
        }
    }
}