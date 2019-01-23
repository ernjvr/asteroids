package com.ernjvr.asteroids

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class GameOverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        val tvFinalScore = findViewById<TextView>(R.id.tvFinalScore)
        tvFinalScore.text = String.format(Integer.toString(intent.getIntExtra(getString(R.string.score), 0)))

        val startButton = findViewById<Button>(R.id.btnGameOverStart)

        startButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }
}
