package com.ernjvr.asteroids.engine

import android.view.SurfaceHolder
import android.view.SurfaceView
import com.ernjvr.asteroids.R

class GameThread(private val surfaceHolder: SurfaceHolder, private val view: SurfaceView) : Thread() {
    var running = false
    private var interval = (R.string.game_rate_interval).toLong()

    override fun run() {
        while (running) {
            val canvas = surfaceHolder.lockCanvas()
            try {
                synchronized(surfaceHolder) {
                    view.draw(canvas)
                }
            } finally {
                surfaceHolder.unlockCanvasAndPost(canvas)
            }
            sleep(interval)
        }
    }
}