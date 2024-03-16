package com.example.note_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.note_sqlite.databinding.ActivityAddNoteBinding

private lateinit var binding: ActivityAddNoteBinding
private lateinit var db: NoteDatabaseHelper
class addNote : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)

        binding.idSaveNote.setOnClickListener{
            val title= binding.idTitle.text.toString()
            val content= binding.idContent.text.toString()
            val note= Note(0, title,  content)
            db.insertNOte(note)
            finish()
            Toast.makeText(this, "Add Success", Toast.LENGTH_SHORT).show()
        }

        binding.idButtonBack.setOnClickListener {
            finish()
        }
    }
}