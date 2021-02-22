package com.school21.ft_hangouts.sms

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASENAME = "db_ft_hangouts_sms"
val TABLENAME = "Sms"

var COL_ID : String = "id"
var COL_TIME : String = "time"
var COL_TARGET : String = "target"
var COL_INPUT : String = "input"
var COL_MESSAGE : String = "message"

class SMSDataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLENAME (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_TIME VARCHAR(256)," +
                "$COL_TARGET VARCHAR(256)," +
                "$COL_INPUT VARCHAR(256)," +
                "$COL_MESSAGE VARCHAR(256))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun insertData(message: Message) {
        val database = this.writableDatabase
        val contentValues = GetContentValues(message)
        val result = database.insert(TABLENAME, null, contentValues)
        if (result == (0).toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
    }

    private fun GetContentValues(message: Message): ContentValues? {
        val contentValues = ContentValues()
        contentValues.put(COL_TIME, message.createdAt.toString())
        contentValues.put(COL_TARGET, message.sender)
        contentValues.put(COL_INPUT, message.input.toString())
        contentValues.put(COL_MESSAGE, message.message)
        return contentValues
    }

    fun readData(phone: String): MutableList<Message> {
        val list: MutableList<Message> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME where $COL_TARGET = '$phone'"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val message = Message()
                message.createdAt = result.getString(result.getColumnIndex(COL_TIME)).toLong()
                message.sender = result.getString(result.getColumnIndex(COL_TARGET))
                message.input = result.getString(result.getColumnIndex(COL_INPUT)).toBoolean()
                message.message = result.getString(result.getColumnIndex(COL_MESSAGE))
                list.add(message)
            }
            while (result.moveToNext())
        }
        return list
    }

    fun updateMessage(id: Long, message: Message) {
        val db = this.writableDatabase
        val cv = GetContentValues(message)
        db.update(TABLENAME, cv, "$COL_ID = ?", arrayOf(id.toString()))
    }

    fun deleteMessage(id: Long){
        val db = this.writableDatabase
        db.delete(TABLENAME, "$COL_ID = ?", arrayOf(id.toString()));
    }
}