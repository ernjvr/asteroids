package com.ernjvr.asteroids.data.dao

interface HighScoreDAO {
    fun add(score: Int): Long
    fun read(): Int
    fun update(score: Int): Int
}