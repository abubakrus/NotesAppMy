package com.example.notesappmy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.SearchView
import com.example.notesappmy.databinding.ActivityMainBinding
import com.example.notesappmy.databinding.ActivitySimpleNoteBinding
import com.example.notesappmy.db.Datebase
import com.example.notesappmy.models.Note

class MainActivity() : AppCompatActivity()//    SearchView.OnQueryTextListener, Parcelable{
{    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val datebase by lazy {
        Datebase(this)
    }
    private val adapter = NotesAdapter(
        navigateToSimpleNoteActivityDetailsScreen = ::navigateToSimpleNoteActivityDetailsScreen,
    )

    private val allNotesList by lazy {
        datebase.getAllNotes().toMutableList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//    SEarchView    binding.searchView.setOnQueryTextListener(this)
        adapter.updateList(allNotesList)
        binding.plusButton.setOnClickListener {
            val note = datebase.saveNewNote(isSimpleNote = "")
            allNotesList.add(note)
            adapter.updateList(allNotesList)
            navigateToSimpleNoteActivityDetailsScreen(note)
        }
    }

    private fun navigateToSimpleNoteActivityDetailsScreen(note: Note) {
        val intent = Intent(this, ActivitySimpleNoteBinding::class.java)
        intent.putExtra(NOTE_KEY, note)
        startActivity(intent)
    }
    override fun onStart() {
        super.onStart()
        adapter.updateList(datebase.getAllNotes())
    }

}
const val NOTE_KEY = "NOTE_KEY"