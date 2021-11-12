package com.anurag.notekeepingapp.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import com.anurag.notekeepingapp.R
import com.anurag.notekeepingapp.adapters.NoteAdapter
import com.anurag.notekeepingapp.databinding.FragmentNoteListBinding
import com.anurag.notekeepingapp.recyclerviewselection.NoteItemDetailsLookup
import com.anurag.notekeepingapp.recyclerviewselection.NoteItemKeyProvider
import com.anurag.notekeepingapp.viewmodels.NoteListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NoteListFragment : Fragment() {

    private var _binding: FragmentNoteListBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    private var _navController: NavController? = null
    private val navController get() = _navController!!

    private var _selectionTracker: SelectionTracker<Long>? = null
    private val selectionTracker get() = _selectionTracker!!

    private val viewModel: NoteListViewModel by viewModels()

    private var actionMode: ActionMode? = null
    private var actionModeCallback: ActionModeCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        if (savedInstanceState != null) {
            _selectionTracker?.onRestoreInstanceState(savedInstanceState)
        }
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
        registerForContextMenu(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NoteAdapter()

        val recyclerView = binding.myRecyclerView
        recyclerView.adapter = adapter

        _navController = findNavController()

        binding.floatingActionButton.setOnClickListener {
            val action = NoteListFragmentDirections
                .actionNoteListDestToEditNoteDest()

            navController.navigate(action)
        }

        viewModel.allNotes.observe(viewLifecycleOwner, { notes ->
            notes.let {
                adapter.submitList(notes)

                if (notes.isNotEmpty()) {
                    binding.noNotesView.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                } else {
                    recyclerView.visibility = View.GONE
                    binding.noNotesView.visibility = View.VISIBLE
                }

                _selectionTracker = SelectionTracker.Builder(
                    "note-selection",
                    recyclerView,
                    NoteItemKeyProvider(notes),
                    NoteItemDetailsLookup(recyclerView),
                    StorageStrategy.createLongStorage()
                ).withSelectionPredicate(
                    SelectionPredicates.createSelectAnything()
                ).build()

                adapter.selectionTracker = selectionTracker
                actionModeCallback = ActionModeCallback(selectionTracker)

                selectionTracker.let {
                    it.addObserver(
                        object : SelectionTracker.SelectionObserver<Long>() {

                            override fun onSelectionChanged() {
                                super.onSelectionChanged()
                                if (it.hasSelection() && actionMode == null) {
                                    actionMode = requireActivity()
                                        .startActionMode(actionModeCallback)

                                    actionMode!!.title =
                                        it.selection.size().toString()

                                } else if (!it.hasSelection() &&
                                    actionMode != null
                                ) {
                                    actionMode!!.finish()
                                    actionMode = null
                                } else {
                                    actionMode!!.title =
                                        it.selection.size().toString()
                                }
                            }
                        })
                }
            }
        })
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        _selectionTracker?.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _navController = null
        _selectionTracker = null
        actionMode = null
        actionModeCallback = null
    }

    private class ActionModeCallback(
        private val selectionTracker: SelectionTracker<Long>
    ) : ActionMode.Callback {
        // Called when the action mode is created; startActionMode() was called
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            // Inflate a menu resource providing context menu items
            val inflater: MenuInflater = mode.menuInflater
            inflater.inflate(R.menu.menu_top_action_mode, menu)
            return true
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.action_share -> {
                    mode.finish() // Action picked, so close the CAB
                    true
                }
                else -> false
            }
        }

        // Called when the user exits the action mode
        override fun onDestroyActionMode(mode: ActionMode) {
            selectionTracker.clearSelection()
        }
    }

}