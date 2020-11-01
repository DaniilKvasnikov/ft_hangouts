package com.school21.ft_hangouts

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASENAME = "db_ft_hangouts"
val TABLENAME = "Users"

var COL_ID : String = "id"
var COL_NAME : String = "name"
var COL_SURNAME : String = "surname"
var COL_PHONE : String = "phone"
var COL_ORGANIZATION : String = "organization"
var COL_EMAIL : String = "email"

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLENAME (" +
                                    "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                                    "$COL_NAME VARCHAR(256)," +
                                    "$COL_SURNAME VARCHAR(256)," +
                                    "$COL_PHONE VARCHAR(256)," +
                                    "$COL_ORGANIZATION VARCHAR(256)," +
                                    "$COL_EMAIL VARCHAR(256))"
        db?.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
    fun insertData(user: User) {
        val database = this.writableDatabase
        val contentValues = GetContentValues(user)
        val result = database.insert(TABLENAME, null, contentValues)
        if (result == (0).toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    }

    private fun GetContentValues(user: User): ContentValues? {
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, user.name)
        contentValues.put(COL_SURNAME, user.surname)
        contentValues.put(COL_PHONE, user.phone)
        contentValues.put(COL_ORGANIZATION, user.organization)
        contentValues.put(COL_EMAIL, user.email)
        return contentValues
    }

    fun readData(): MutableList<User> {
        val list: MutableList<User> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val user = User()
                user.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                user.name = result.getString(result.getColumnIndex(COL_NAME))
                user.surname = result.getString(result.getColumnIndex(COL_SURNAME))
                user.phone = result.getString(result.getColumnIndex(COL_PHONE))
                user.organization = result.getString(result.getColumnIndex(COL_ORGANIZATION))
                user.email = result.getString(result.getColumnIndex(COL_EMAIL))
                list.add(user)
            }
            while (result.moveToNext())
        }
        return list
    }

    fun updateUser(id: Long, user: User) {
        val db = this.writableDatabase
        val cv = GetContentValues(user)
        db.update(TABLENAME, cv, "$COL_ID = ?", arrayOf(id.toString()))
    }

    fun deleteUser(id: Long){
        val db = this.writableDatabase
        db.delete(TABLENAME, "$COL_ID = ?", arrayOf(id.toString()));
    }
}