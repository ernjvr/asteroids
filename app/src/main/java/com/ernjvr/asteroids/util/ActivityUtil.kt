package com.ernjvr.asteroids.util

import android.content.Intent
import android.support.v7.app.AppCompatActivity

class ActivityUtil {

    companion object {

        fun switchTo(clazz: Class<*>, activity: AppCompatActivity, flags: List<Int>? = null) {
            val intent = Intent(activity, clazz)
            flags?.forEach {
                intent.addFlags(it)
            }
            activity.startActivity(intent)
        }
    }
}