package com.anurag.notekeepingapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.anurag.notekeepingapp.adapters.RecyclerViewAdapter
import com.anurag.notekeepingapp.databinding.FragmentNoteListBinding
import com.anurag.notekeepingapp.viewmodels.NoteListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteListFragment : Fragment() {

    private var _binding: FragmentNoteListBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    private val viewModel: NoteListViewModel by viewModels()

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

        val navController = findNavController()

        val adapter = RecyclerViewAdapter { note ->
            viewModel.delete(note)
        }

        binding.myRecyclerView.adapter = adapter

        viewModel.allNotes.observe(viewLifecycleOwner, { notes ->
            notes?.let {
                adapter.updateNotes(notes)
            }
        })

        binding.floatingActionButton.setOnClickListener {
            val action = NoteListFragmentDirections
                .actionNoteListDestToEditNoteFragment()
            navController.navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}