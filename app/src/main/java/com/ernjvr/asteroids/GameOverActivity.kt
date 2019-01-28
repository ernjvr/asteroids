package com.ernjvr.asteroids

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.ernjvr.asteroids.data.dao.HighScoreDAO
import com.ernjvr.asteroids.data.dao.HighScoreDAOImpl
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class GameOverActivity : AppCompatActivity() {

    private val highScoreDAO: HighScoreDAO = HighScoreDAOImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        val score = intent.getIntExtra(getString(R.string.score), 0)
        confirmHighScore(score)

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

    private fun confirmHighScore(score: Int) {
        doAsync {
            val highScore = highScoreDAO.read()
            var isHighScore = true

            when {
                highScore == 0 -> highScoreDAO.add(score)
                score > highScore -> highScoreDAO.update(score)
                else -> isHighScore = false
            }

            if (isHighScore) {
                uiThread {
                    findViewById<TextView>(R.id.tvHighScore).visibility = View.VISIBLE
                }
            }
        }
    }
}
