package com.ernjvr.asteroids

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ernjvr.asteroids.view.ActivityProvider

class GameActivity : AppCompatActivity(), ActivityProvider {

    lateinit var tvScore:TextView
    lateinit var tvLives:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        tvScore = findViewById(R.id.tvScore)
        tvLives = findViewById(R.id.tvLives)
    }

    override fun currentActivity(): AppCompatActivity {
        return this
    }
}