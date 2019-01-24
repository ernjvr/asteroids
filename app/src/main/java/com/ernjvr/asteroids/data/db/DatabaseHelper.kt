package com.ernjvr.asteroids.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DELETE)
        onCreate(db)
    }

    companion object {
        private const val CREATE = "CREATE TABLE ${HighScoreEntry.TABLE_NAME} (" +
                "${HighScoreEntry.ID_COL} INTEGER PRIMARY KEY," +
                "${HighScoreEntry.SCORE_COL} INTEGER" +
                ")"
        private const val DELETE = "DROP TABLE IF EXISTS ${HighScoreEntry.TABLE_NAME}"
    }
}