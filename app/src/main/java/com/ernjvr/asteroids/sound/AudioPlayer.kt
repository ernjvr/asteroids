package com.ernjvr.asteroids.sound

import android.media.AudioAttributes
import android.media.SoundPool
import android.support.v7.app.AppCompatActivity
import com.ernjvr.asteroids.R

class AudioPlayer(activity: AppCompatActivity) {

    private val soundPool: SoundPool
    private val audioMap: Map<Int, Int>

    init {
        val attributeBuilder = AudioAttributes.Builder()
        attributeBuilder.setUsage(AudioAttributes.USAGE_GAME)
        soundPool = SoundPool.Builder().setMaxStreams(2).setAudioAttributes(attributeBuilder.build()).build()
        audioMap = mapOf(
            Pair(AMBIENCE, soundPool.load(activity, R.raw.ambience, 1)),
            Pair(EXPLOSION, soundPool.load(activity, R.raw.explosion, 1))
        )
    }

    fun playAudio(audioId: Int, loop: Int = 0) {
        soundPool.play(audioMap.getValue(audioId), 1F, 1F, 1, loop, 1F)
    }

    companion object {
        const val EXPLOSION = R.raw.explosion
        const val AMBIENCE  = R.raw.ambience
    }
}