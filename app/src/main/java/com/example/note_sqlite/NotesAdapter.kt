package com.example.note_sqlite

import android.content.Context
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(var listNote: List<Note>, context: Context):
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db: NoteDatabaseHelper= NoteDatabaseHelper(context)

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titleView: TextView= itemView.findViewById(R.id.titleId)
        val contentView: TextView= itemView.findViewById(R.id.contentId)
        val EditView: ImageView = itemView.findViewById(R.id.editId)
        val deleteView: ImageView= itemView.findViewById(R.id.deleteId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view= LayoutInflater.from(parent.context)
        .inflate(R.layout.layout_item_list, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note= listNote[position]
        holder.titleView.text= note.title
        holder.contentView.text= note.content

        holder.EditView.setOnClickListener{
            val intent= Intent(holder.itemView.context, updateActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteView.setOnClickListener {
            db.deleteNote(note.id)
            refreshData(db.getAllList())
            Toast.makeText(holder.itemView.context, "Delete Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newListNote: List<Note>){
        listNote= newListNote
        notifyDataSetChanged()
    }
}