package com.example.notesappmy.models

import java.io.Serializable

data class Note(
    val title: String,
    val descripition: String,
) : Serializable