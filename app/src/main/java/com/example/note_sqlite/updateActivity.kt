package com.example.note_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.note_sqlite.databinding.ActivityUpdateBinding

private lateinit var binding: ActivityUpdateBinding
private lateinit var db: NoteDatabaseHelper
class updateActivity : AppCompatActivity() {

    private var id: Int=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= NoteDatabaseHelper(this)

        id = intent.getIntExtra("note_id", -1)
        if(id==-1){
            finish()
            return
        }else{
            val note= db.getNoteById(id)
            binding.idButtonBack.setOnClickListener {
                finish()
            }
            binding.idEditTitle.setText(note.title)
            binding.idEditContent.setText(note.content)

            binding.idEditSaveNote.setOnClickListener{
                val newTitle= binding.idEditTitle.text.toString()
                val newContent= binding.idEditContent.text.toString()
                val updateNote= Note(id, newTitle, newContent)
                db.updateNote(updateNote)
                finish()
                Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show()
            }
        }

    }
}