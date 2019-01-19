package com.ernjvr.asteroids.model

class Asteroid(
    var color: Int = 0, var xx: Float = 0F, var yy: Float = 0F, var xxx: Float = 0F, var yyy: Float = 0F
    , val RADIUS: Float = 20F
) {

    fun moveBall(width: Int, height: Int) {
        xx += xxx
        yy += yyy

        // beyond right margin
        if (xx > width - RADIUS) {
            val overshoot = xx - (width - RADIUS)
            xx -= overshoot * 2
            xxx = -xxx
            // beyond left margin
        } else if (xx < RADIUS) {
            val overshoot = RADIUS - xx
            xx += overshoot * 2
            xxx = -xxx
        }

        // beyond bottom margin
        if (yy > height - RADIUS) {
            val overshoot = yy - (height - RADIUS)
            yy -= overshoot * 2
            yyy = -yyy
            // beyond top margin
        } else if (yy < RADIUS) {
            val overshoot = RADIUS - yy
            yy += overshoot * 2
            yyy = -yyy
        }
    }
}