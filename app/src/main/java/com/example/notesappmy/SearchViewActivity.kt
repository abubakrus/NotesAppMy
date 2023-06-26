package com.example.notesappmy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notesappmy.databinding.ActivitySearchViewBinding

class SearchViewActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySearchViewBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}