package com.ernjvr.asteroids

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.ernjvr.asteroids.data.dao.HighScoreDAO

class GameOverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        val score = intent.getIntExtra(getString(R.string.score), 0)

        if (confirmHighScore(score)) {
            findViewById<TextView>(R.id.tvHighScore).visibility = View.VISIBLE
        }

        val tvFinalScore = findViewById<TextView>(R.id.tvFinalScore)
        tvFinalScore.text = String.format(Integer.toString(score))

        val startButton = findViewById<Button>(R.id.btnGameOverStart)
        startButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }

    private fun confirmHighScore(score: Int): Boolean {
        val dao = HighScoreDAO(this)
        val highScore = dao.read()

        when {
            highScore == 0 -> dao.add(score)
            score > highScore -> dao.update(score)
            else -> return false
        }
        return true
    }
}
