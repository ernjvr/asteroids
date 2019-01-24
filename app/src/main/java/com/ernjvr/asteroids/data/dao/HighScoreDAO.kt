package com.ernjvr.asteroids.data.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.ernjvr.asteroids.data.db.DatabaseHelper
import com.ernjvr.asteroids.data.db.HighScoreEntry

class HighScoreDAO(context: Context) {
    private val tag = HighScoreDAO::class.java.simpleName
    private val dbHelper = DatabaseHelper(context)

    fun add(score: Int): Long {
        val values = ContentValues()
        values.put(HighScoreEntry.SCORE_COL, score)

        val db = dbHelper.writableDatabase
        val id = db.transaction {
            insert(HighScoreEntry.TABLE_NAME, null, values)
        }
        Log.d(tag, "High Score inserted: $score")
        return id
    }

    fun read(): Int {
        val db = dbHelper.readableDatabase
        val cursor = db.doQuery(HighScoreEntry.TABLE_NAME, arrayOf(HighScoreEntry.SCORE_COL))
        return readHighScoreFrom(cursor)
    }

    fun update(score: Int): Int {
        val values = ContentValues()
        values.put(HighScoreEntry.SCORE_COL, score)

        val db = dbHelper.writableDatabase
        val rowsUpdated = db.transaction {
            update(HighScoreEntry.TABLE_NAME, values, null, null)
        }
        Log.d(tag, "High Score updated: $score")
        return rowsUpdated
    }

    private fun readHighScoreFrom(cursor: Cursor): Int {
        cursor.use {
            return if (it.moveToFirst()) {
                it.getInt(HighScoreEntry.SCORE_COL)
            } else {
                0
            }
        }
    }
}

private fun SQLiteDatabase.doQuery(table: String, columns: Array<String>, selection: String? = null,
                                   selectionArgs: Array<String>? = null, groupBy: String? = null,
                                   having: String? = null, orderBy: String? = null): Cursor {
    return query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
}

private fun Cursor.getInt(columnName: String): Int = getInt(getColumnIndex(columnName))

private inline fun <T> SQLiteDatabase.transaction(function: SQLiteDatabase.() -> T): T {
    beginTransaction()
    val result = try {
        val returnValue = function()
        setTransactionSuccessful()
        returnValue
    } finally {
        endTransaction()
    }
    close()
    return result
}