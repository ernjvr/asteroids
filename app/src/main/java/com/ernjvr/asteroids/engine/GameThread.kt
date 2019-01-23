package com.ernjvr.asteroids.engine

import android.view.SurfaceHolder
import com.ernjvr.asteroids.view.GameView

class GameThread(private val surfaceHolder: SurfaceHolder, private val view: GameView) : Thread() {
    var running = false

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
            sleep(200)
        }
    }
}