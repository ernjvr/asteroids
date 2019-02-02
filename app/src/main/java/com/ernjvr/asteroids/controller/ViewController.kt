package com.ernjvr.asteroids.controller

import android.view.MotionEvent

interface ViewController {
    fun receiveTouch(event: MotionEvent?)
    fun receiveSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int)
    fun update()
}