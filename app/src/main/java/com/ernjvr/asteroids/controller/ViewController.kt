package com.ernjvr.asteroids.controller

import android.view.MotionEvent

interface ViewController {
    fun receiveTouch(event: MotionEvent?)
    fun update()
}