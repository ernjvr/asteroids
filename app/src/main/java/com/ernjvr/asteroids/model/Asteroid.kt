package com.ernjvr.asteroids.model

class Asteroid(
    var color: Int = 0, var xx: Float = 0F, var yy: Float = 0F, var xxx: Float = 0F, var yyy: Float = 0F
    , var radius: Float = 20F
) {

    fun move(width: Int, height: Int) {
        xx += xxx
        yy += yyy

        // beyond right margin
        if (xx > width - radius) {
            val overshoot = xx - (width - radius)
            xx -= overshoot * 2
            xxx = -xxx
            // beyond left margin
        } else if (xx < radius) {
            val overshoot = radius - xx
            xx += overshoot * 2
            xxx = -xxx
        }

        // beyond bottom margin
        if (yy > height - radius) {
            val overshoot = yy - (height - radius)
            yy -= overshoot * 2
            yyy = -yyy
            // beyond top margin
        } else if (yy < radius) {
            val overshoot = radius - yy
            yy += overshoot * 2
            yyy = -yyy
        }
    }
}