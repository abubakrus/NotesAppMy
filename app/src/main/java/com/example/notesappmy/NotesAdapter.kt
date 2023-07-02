package com.example.notesappmy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappmy.databinding.RecyclerItemBinding
import com.example.notesappmy.models.Note
import kotlin.reflect.KFunction1

class NotesAdapter(
    private val navigateToSimpleNoteActivityDetailsScreen: KFunction1<Note, Unit>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val noteList = mutableListOf<Note>()

    fun updateList(newList: List<Note>) {
        noteList.clear()
        noteList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class SimpleNoteViewHolder(
        private val binding: RecyclerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.titleRecycler.text = note.title
            binding.root.setOnClickListener {
                navigateToSimpleNoteActivityDetailsScreen(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.recycler_item, parent, false)
        val binding = RecyclerItemBinding.bind(view)
        return SimpleNoteViewHolder(binding)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SimpleNoteViewHolder).bind(noteList[position])
    }

    override fun getItemCount(): Int = noteList.size

    }


