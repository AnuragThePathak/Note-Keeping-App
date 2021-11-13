package com.anurag.notekeepingapp.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.anurag.notekeepingapp.R
import com.anurag.notekeepingapp.adapters.MyItem
import com.anurag.notekeepingapp.databinding.FragmentNoteListBinding
import com.anurag.notekeepingapp.viewmodels.NoteListViewModel
import dagger.hilt.android.AndroidEntryPoint
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.SelectableAdapter
import eu.davidea.flexibleadapter.helpers.ActionModeHelper
import eu.davidea.flexibleadapter.items.IFlexible


@AndroidEntryPoint
class NoteListFragment : Fragment(),
    FlexibleAdapter.OnItemLongClickListener, ActionMode.Callback {

    private var _binding: FragmentNoteListBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    private var _navController: NavController? = null
    private val navController get() = _navController!!

    private var _adapter: FlexibleAdapter<IFlexible<*>>? = null
    val adapter get() = _adapter!!

    private var mActionModeHelper: ActionModeHelper? = null

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
        registerForContextMenu(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myItems = ArrayList<IFlexible<*>>()

        _adapter = FlexibleAdapter(myItems)
        val recyclerView = binding.myRecyclerView
        recyclerView.adapter = adapter

        initializeActionModeHelper(SelectableAdapter.Mode.IDLE)
        adapter.addListener(this)

        viewModel.allNotes.observe(viewLifecycleOwner, { notes ->
            notes.let {
                val list = ArrayList<IFlexible<*>>()
                it.forEach { note ->
                    list.add(MyItem(note))
                }

                adapter.updateDataSet(list, true)

                if (notes.isNotEmpty()) {
                    binding.noNotesView.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                } else {
                    recyclerView.visibility = View.GONE
                    binding.noNotesView.visibility = View.VISIBLE
                }
            }

            _navController = findNavController()

            binding.floatingActionButton.setOnClickListener {
                val action = NoteListFragmentDirections
                    .actionNoteListDestToEditNoteDest()

                navController.navigate(action)
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _navController = null
        _adapter = null
    }

    override fun onItemLongClick(position: Int) {
        mActionModeHelper?.onLongClick(activity as AppCompatActivity?, position)
    }


    private fun initializeActionModeHelper(@SelectableAdapter.Mode mode: Int) {
        //this = ActionMode.Callback instance
        mActionModeHelper = object : ActionModeHelper(
            adapter,
            R.menu.menu_top_action_mode, this
        ) {
            // Override to customize the title
            override fun updateContextTitle(count: Int) {
                // You can use the internal mActionMode instance
                if (mActionMode != null) {
                    mActionMode.title = if (count == 1) "1" else count.toString()
                }
            }
        }.withDefaultMode(mode)
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {

    }
}