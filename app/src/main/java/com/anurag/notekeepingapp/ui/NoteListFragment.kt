package com.anurag.notekeepingapp.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.anurag.notekeepingapp.R
import com.anurag.notekeepingapp.adapters.NoteAdapter
import com.anurag.notekeepingapp.databinding.FragmentNoteListBinding
import com.anurag.notekeepingapp.viewmodels.NoteListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NoteListFragment : Fragment() {

    private var _binding: FragmentNoteListBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    private var _navController: NavController? = null
    private val navController get() = _navController!!

    private var _adapter: NoteAdapter? = null
    private val adapter get() = _adapter!!

    private val viewModel: NoteListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(
            inflater, container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _adapter = NoteAdapter()
        val recyclerView = binding.myRecyclerView
        recyclerView.adapter = adapter

        val stub = view.findViewById<ViewStub>(R.id.stub_import)

        viewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            notes.let {
                adapter.submitList(it)

                if (notes.isNotEmpty()) {

                    stub.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                } else {
                    recyclerView.visibility = View.GONE
                    stub.visibility = View.VISIBLE
                }
            }

            _navController = findNavController()

            binding.floatingActionButton.setOnClickListener {
                val action = NoteListFragmentDirections
                    .actionNoteListDestToEditNoteDest()

                navController.navigate(action)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_top_note_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_settings -> {
                val action = NoteListFragmentDirections
                    .actionNoteListDestToSettingsDest()

                navController.navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _navController = null
        _adapter = null
    }
}