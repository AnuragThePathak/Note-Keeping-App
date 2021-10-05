package com.anurag.notekeepingapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.anurag.notekeepingapp.*
import com.anurag.notekeepingapp.data.NoteEntity
import com.anurag.notekeepingapp.databinding.FragmentNoteListBinding

class NoteListFragment : Fragment(R.layout.fragment_note_list), OnTapHandler {

    private var _binding: FragmentNoteListBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    private val viewModel: NoteListViewModel by viewModels {
        NoteListViewModelFactory(
            (activity?.application as NoteApplication)
                .repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RecyclerViewAdapter(this)
        binding.myRecyclerView.adapter = adapter

        viewModel.allNotes.observe(viewLifecycleOwner, { notes ->
            notes?.let {
                adapter.updateNotes(notes)
            }
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_note_list_dest_to_editNoteFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(note: NoteEntity) {
        viewModel.delete(note)
    }
}