package com.ernjvr.asteroids.model

import android.graphics.Color

class AsteroidFactory {

    private val xs = listOf(40F, 65F, 90F)
    private val ys = listOf(40F, 65F, 90F)
    private val xxs = listOf(10F, 25F, 40F)
    private val yys = listOf(10F, 25F, 40F)
    private val colors = listOf(Color.RED, Color.YELLOW, Color.GREEN)
    val asteroids = listOf(Asteroid(), Asteroid(RADIUS = 30F), Asteroid(RADIUS = 40F))

    init {
        asteroids.forEachIndexed { index, ball ->
            ball.color = colors[index]
            ball.xx = xs[index]
            ball.yy = ys[index]
            ball.xxx = xxs[index]
            ball.yyy = yys[index]
        }
    }
//
//    companion object {
//        val asteroids = listOf(Asteroid(), Asteroid(RADIUS = 30F), Asteroid(RADIUS = 40F))
//    }
}