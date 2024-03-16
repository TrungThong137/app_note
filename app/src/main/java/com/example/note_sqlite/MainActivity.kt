package com.example.note_sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.note_sqlite.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding
private lateinit var db: NoteDatabaseHelper
private lateinit var noteAdapter: NotesAdapter
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= NoteDatabaseHelper(this)
        noteAdapter= NotesAdapter(db.getAllList(), this)

        binding.listViewId.layoutManager= StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL,)
        binding.listViewId.adapter= noteAdapter

        binding.idAdd.setOnClickListener{
            val intent = Intent(this@MainActivity, addNote::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        noteAdapter.refreshData(db.getAllList())
    }
}