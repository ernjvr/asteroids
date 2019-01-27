package com.ernjvr.asteroids.model

class Asteroid(var color: Int = 0, var x: Float = 0F, var y: Float = 0F, var velocityX: Float = 0F,
               var velocityY: Float = 0F, var radius: Float = 20F) {

    fun move(width: Int, height: Int) {
        x += velocityX
        y += velocityY

        // beyond right margin
        if (x > width - radius) {
            val overshoot = x - (width - radius)
            x -= overshoot * 2
            velocityX = -velocityX

            // beyond left margin
        } else if (x < radius) {
            val overshoot = radius - x
            x += overshoot * 2
            velocityX = -velocityX
        }

        // beyond bottom margin
        if (y > height - radius) {
            val overshoot = y - (height - radius)
            y -= overshoot * 2
            velocityY = -velocityY

            // beyond top margin
        } else if (y < radius) {
            val overshoot = radius - y
            y += overshoot * 2
            velocityY = -velocityY
        }
    }
}