package com.example.notesappmy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesappmy.databinding.ActivityMainBinding
import com.example.notesappmy.databinding.ActivitySimpleNoteBinding
import com.example.notesappmy.db.Datebase
import com.example.notesappmy.models.Note

class SimpleNoteActivityDetails : AppCompatActivity() {
    private val binding: ActivitySimpleNoteBinding by lazy {
        ActivitySimpleNoteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val note = intent.extras?.getSerializable(NOTE_KEY) as? Note
        initViews(note)
        val database = Datebase(this)
        binding.saveButton.setOnClickListener {
            database.updateSimpleNote(
                oldNote = note!!,
                title = binding.titleEditView.text.toString(),
                description = binding.descripitionEditView.text.toString(),
            )
        }
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        showToast(message = "Ваша заметка успешно обнавлена")
    }

    private fun initViews(note: Note?) {
        if (note == null) return
        binding.titleEditView.setText(note.title)
        binding.descripitionEditView.setText(note.descripition)
    }
    private fun showToast(message:String){
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}