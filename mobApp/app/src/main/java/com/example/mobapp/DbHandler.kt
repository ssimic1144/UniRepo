package com.example.mobapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.lang.Exception
import java.util.prefs.PreferencesFactory

class DbHandler(context: Context, name : String?, factory: SQLiteDatabase.CursorFactory?, version : Int) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    companion object{
        private val DATABASE_NAME = "DB.db"
        private val DATABASE_VERSION = 2

        val EVENT_TABLE_NAME = "event"
        val COLUMN_EVENTID = "eventid"
        val COLUMN_EVENTNAME = "eventname"
        val COLUMN_EVENTCAPACITY= "eventcapacity"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_EVENT_TABLE = ("CREATE TABLE $EVENT_TABLE_NAME (" + "$COLUMN_EVENTID INTEGER PRIMARY KEY AUTOINCREMENT, "+"$COLUMN_EVENTNAME TEXT,"+"$COLUMN_EVENTCAPACITY TEXT)")
        db?.execSQL(CREATE_EVENT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun getEvents(mCtx : Context) : ArrayList<Event>{
        val qry = "Select * from $EVENT_TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry, null)
        val events = ArrayList<Event>()

        println(qry)
        println(cursor.count)

        if (cursor.count == 0){
            Toast.makeText(mCtx, "No records", Toast.LENGTH_SHORT).show()

        }else{
            while (cursor.moveToNext()){
                val event = Event()
                event.eventId = cursor.getInt(cursor.getColumnIndex(COLUMN_EVENTID))
                event.eventName = cursor.getString(cursor.getColumnIndex(COLUMN_EVENTNAME))
                event.eventCapacity = cursor.getString(cursor.getColumnIndex(COLUMN_EVENTCAPACITY))

                events.add(event)
            }

            Toast.makeText(mCtx, "${cursor.count.toString()} records found", Toast.LENGTH_SHORT).show()

        }
        cursor.close()
        db.close()
        return events

    }

    fun addEvent(mCtx: Context, event: Event){
        val values = ContentValues()
        values.put(COLUMN_EVENTNAME, event.eventName)
        values.put(COLUMN_EVENTCAPACITY, event.eventCapacity)

        val db = this.writableDatabase
        try {
            db.insert(EVENT_TABLE_NAME, null, values)
            Toast.makeText(mCtx, "Event Added!", Toast.LENGTH_SHORT).show()
        }catch (e : Exception){
            Toast.makeText(mCtx, e.message, Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun deleteEvent(eventId : Int) : Boolean{
        val qry = "DELETE FROM $EVENT_TABLE_NAME WHERE $COLUMN_EVENTID = $eventId"
        val db = this.writableDatabase

        var result : Boolean = false

        try {
            val cursor = db.execSQL(qry)
            result = true
        }catch (e : Exception){
            println("Error deleting")
        }
        db.close()
        return result
    }

    fun updateEvent(id : String, eventName : String, eventCapacity : String) : Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_EVENTNAME,eventName)
        contentValues.put(COLUMN_EVENTCAPACITY, eventCapacity)
        var res = false
        try {
            db.update(EVENT_TABLE_NAME, contentValues, "$COLUMN_EVENTID = ?", arrayOf(id))
            res = true
        }catch (e : Exception) {
            println("Error updating")
            res = false
        }

        return res

    }
}