package com.example.note_sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DatabaseName, null, DatabaseVersion) {

    companion object{
        private const val DatabaseName= "notesapp.db"
        private const val DatabaseVersion= 1
        private const val TABLE_NAME= "allNote"
        private const val Column_Id= "id"
        private const val Column_Title= "title"
        private const val Column_Content= "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME($Column_Id INTEGER PRIMARY KEY, $Column_Title TEXT, $Column_Content TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery= "drop table if exists $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertNOte(note: Note){
        val db= writableDatabase
        val values= ContentValues().apply {
            put(Column_Title, note.title)
            put(Column_Content, note.content)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllList(): List<Note>{
        val noteList= mutableListOf<Note>()
        val db= readableDatabase
        val query= "Select * from $TABLE_NAME"
        val cusor= db.rawQuery(query, null)

        while (cusor.moveToNext()){
            val id= cusor.getInt(cusor.getColumnIndexOrThrow(Column_Id))
            val title= cusor.getString(cusor.getColumnIndexOrThrow(Column_Title))
            val content= cusor.getString(cusor.getColumnIndexOrThrow(Column_Content))

            val note= Note(id, title, content)
            noteList.add(note)
        }
        cusor.close()
        db.close()
        return noteList
    }

    fun updateNote(note: Note){
        val db= writableDatabase
        val values= ContentValues().apply {
            put(Column_Title, note.title)
            put(Column_Content, note.content)
        }
        val whereClause= "$Column_Id= ?"
        val whereArgs = arrayOf(note.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun deleteNote(id: Int){
        val db= writableDatabase
        val whereClause= "$Column_Id= ?"
        val whereArgs = arrayOf(id.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    fun getNoteById(noteID: Int): Note{
        val db= readableDatabase
        val query= "Select * from $TABLE_NAME where $Column_Id= $noteID"
        val cursor= db.rawQuery(query, null)
        cursor.moveToFirst()

        val id= cursor.getInt(cursor.getColumnIndexOrThrow(Column_Id))
        val title= cursor.getString(cursor.getColumnIndexOrThrow(Column_Title))
        val content= cursor.getString(cursor.getColumnIndexOrThrow(Column_Content))

        cursor.close()
        db.close()
        return Note(id, title, content)
    }

}