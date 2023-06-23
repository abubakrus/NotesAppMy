package com.example.notesappmy.db

import android.content.Context
import com.example.notesappmy.models.Note
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

private const val NOTES_SHARED_PREF_KEY = "NOTES_SHARED_PREF_KEY"
private const val ALL_NOTES_KEY = "ALL_NOTES_KEY"

class Datebase (
    private val context:Context
    ){
    private val sharedPreferences =context.getSharedPreferences(
        NOTES_SHARED_PREF_KEY,
        Context.MODE_PRIVATE
    )
    fun getAllNotes():List<Note>{
        val gson = Gson()
        val json = sharedPreferences.getString(ALL_NOTES_KEY, null)
        val type : Type = object : TypeToken<ArrayList<Note?>?>() {}.type
        val list = gson.fromJson<List<Note>>(json, type)
        return list ?: emptyList()
    }
    fun saveNewNote(isSimpleNote : String): Note {

        val note = Note(
            title = "111",
            descripition = "12",
        )
        val allNotes = getAllNotes().toMutableList()
        allNotes.add(0, note)
        val gson = Gson().toJson(allNotes)
        sharedPreferences
            .edit()
            .putString(ALL_NOTES_KEY, gson)
            .apply()
        return note
    }
    fun updateSimpleNote(
        oldNote:Note,
        title:String,
        description:String,
    ){
        val allNotes  = getAllNotes().toMutableList()
        val index = allNotes.indexOf(oldNote)
        if (index == -1)return
        val newNote = oldNote.copy(
            title = title,
            descripition = description,
        )
        allNotes[index] = newNote
        val gson = Gson().toJson(allNotes)
        sharedPreferences
            .edit()
            .putString(ALL_NOTES_KEY, gson)
            .apply()
    }
}





