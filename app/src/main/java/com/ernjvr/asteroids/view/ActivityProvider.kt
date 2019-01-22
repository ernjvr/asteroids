package com.ernjvr.asteroids.view

import android.support.v7.app.AppCompatActivity

interface ActivityProvider {
    fun currentActivity(): AppCompatActivity
}