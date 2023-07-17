package com.example.notesappmy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.core.view.isVisible
import com.example.notesappmy.databinding.ActivitySearchViewBinding
import com.example.notesappmy.db.Database
import com.example.notesappmy.models.Note

class SearchViewActivity: AppCompatActivity(),
SearchView.OnQueryTextListener {
    private val binding by lazy {
        ActivitySearchViewBinding.inflate(layoutInflater)
    }
    private val adapter  = NotesAdapter(
        navigateToSimpleNoteActivityDetailsScreen = ::navigateSearchActivityScreen,
    )

    private val database by lazy {
        Database(this)
    }
    private val allNoteList by lazy {
        Database(this).getAllNotes().toMutableList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.searchView.setOnQueryTextListener(this)
        binding.recyclerViewInSearch.adapter = adapter
    }
    private fun startSearch(query:String){
        if (query.isEmpty()){
            binding.searchImage.isVisible = true
            binding.recyclerViewInSearch.isVisible = false
            return
        }else{
            binding.searchImage.isVisible = false
            binding.recyclerViewInSearch.isVisible = true

            val sortedNoteList:List<Note> = allNoteList.filter { note: Note ->
                val isSort = note.title.contains(query, ignoreCase = true)
                isSort
            }
            adapter.updateList(sortedNoteList)
        }
    }

//    private fun navigateToSearchActivityScreen(){
//        val intent = Intent(this, SimpleNoteActivityDetails::class.java)
//        startActivity(intent)
//    }

    private fun navigateSearchActivityScreen(note: Note) {
    val intent = Intent(this, SimpleNoteActivityDetails::class.java)
    intent.putExtra("NOTE_KEY", note)
    startActivity(intent)
}

    override fun onQueryTextSubmit(query: String?): Boolean {
        val searchString = query  ?: return false
        startSearch(searchString)
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        val searchString= query ?:return false
        startSearch(searchString)
        return false
    }

    override fun onStart() {
        super.onStart()
        adapter.updateList(database.getAllNotes())

    }

}