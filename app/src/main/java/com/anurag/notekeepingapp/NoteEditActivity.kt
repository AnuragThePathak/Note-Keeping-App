package com.anurag.notekeepingapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.anurag.notekeepingapp.databinding.ActivityNoteEditBinding

class NoteEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteEditBinding

    private val viewModel: EditNoteViewModel by viewModels {
        EditNoteViewModelFactory((application as NoteApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoteEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStop() {
        super.onStop()

        val noteText = binding.addNoteView.text.toString()
        if (noteText.isNotEmpty()) {
            viewModel.insert(NoteEntity(note = noteText))
        }
    }

    fun addNewNote(view: android.view.View) {
        val noteText = binding.addNoteView.text.toString()
        if (noteText.isNotEmpty()) {
            viewModel.insert(NoteEntity(note = noteText))
        }
    }
}