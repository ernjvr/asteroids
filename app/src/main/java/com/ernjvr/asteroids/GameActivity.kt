package com.ernjvr.asteroids

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ernjvr.asteroids.view.ActivityProvider

class GameActivity : AppCompatActivity(), ActivityProvider {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
    }

    override fun currentActivity(): AppCompatActivity {
        return this
    }
}