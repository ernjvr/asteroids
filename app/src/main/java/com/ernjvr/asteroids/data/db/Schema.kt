package com.ernjvr.asteroids.data.db

import android.provider.BaseColumns

const val DATABASE_NAME = "asteroids.db"
const val DATABASE_VERSION = 1

object HighScoreEntry: BaseColumns {
    const val TABLE_NAME = "high_score"
    const val ID_COL = "id"
    const val SCORE_COL = "score"
}
