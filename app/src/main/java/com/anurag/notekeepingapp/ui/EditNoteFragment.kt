package com.anurag.notekeepingapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anurag.notekeepingapp.EditNoteViewModel
import com.anurag.notekeepingapp.EditNoteViewModelFactory
import com.anurag.notekeepingapp.NoteApplication
import com.anurag.notekeepingapp.R
import com.anurag.notekeepingapp.data.NoteEntity
import com.anurag.notekeepingapp.databinding.FragmentEditNoteBinding

class EditNoteFragment : Fragment(R.layout.fragment_edit_note) {
    private var _binding: FragmentEditNoteBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: EditNoteViewModel by viewModels {
        EditNoteViewModelFactory((activity?.application as NoteApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStop() {
        super.onStop()

        val noteText = binding.addNoteView.text.toString()
        if (noteText.isNotEmpty()) {
            viewModel.insert(NoteEntity(note = noteText))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}