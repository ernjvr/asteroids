package com.ernjvr.asteroids.valitation

import com.ernjvr.asteroids.model.Asteroid

class GameValidator(private val asteroids: List<Asteroid>) {

    fun isCollided(touchX: Float, touchY: Float, radius: Float): Boolean {

        asteroids.forEach {

            if (touchX + radius + it.radius > it.x
                && touchX < it.x + radius + it.radius
                && touchY + radius + it.radius > it.y
                && touchY < it.y + radius + it.radius){

                val distance = Math.sqrt(((touchX - it.x) * (touchX - it.x)).toDouble()
                        + ((touchY - it.y) * (touchY - it.y)))

                if (distance < radius + it.radius){
                    return true
                }
            }
        }
        return false
    }
}