package com.example.notesappmy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappmy.databinding.ActivityMainBinding
import com.example.notesappmy.databinding.ActivitySearchViewBinding
import com.example.notesappmy.databinding.ActivitySimpleNoteBinding
import com.example.notesappmy.db.Database
import com.example.notesappmy.models.Note

class MainActivity() : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val database by lazy {
        Database(this)
    }
    private val adapter: NotesAdapter by lazy {
        NotesAdapter(navigateToSimpleNoteActivityDetailsScreen = ::navigateToSimpleNoteActivityDetailsScreen)
    }


    private val allNotesList by lazy {
        Database(this).getAllNotes().toMutableList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter.updateList(allNotesList)
        binding.recyclerView.adapter = adapter
        binding.plusButton.setOnClickListener {
            val note = database.saveNewNote()
            allNotesList.add(note)
            adapter.updateList(allNotesList)
            navigateToSimpleNoteActivityDetailsScreen(note)
        }
        binding.searchButton.setOnClickListener {
            navigateToSearchViewScreen()
        }
        val swipeToDeleteCallBack = object : ItemTouchHelperCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                allNotesList.removeAt(position)
                adapter.updateList(allNotesList)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun navigateToSimpleNoteActivityDetailsScreen(note: Note) {
        val intent = Intent(this, SimpleNoteActivityDetails::class.java)
        intent.putExtra(NOTE_KEY, note)
        startActivity(intent)
    }

    private fun navigateToSearchViewScreen() {
        val intent = Intent(this, SearchViewActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        adapter.updateList(database.getAllNotes())
    }


}
const val NOTE_KEY = "NOTE_KEY"