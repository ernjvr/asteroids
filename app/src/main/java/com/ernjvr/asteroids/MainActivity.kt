package com.ernjvr.asteroids

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ernjvr.asteroids.view.ActivityProvider

class MainActivity : AppCompatActivity(), ActivityProvider {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun currentActivity(): AppCompatActivity {
        return this
    }
}
