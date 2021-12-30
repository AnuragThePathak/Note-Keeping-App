package com.anurag.notekeepingapp.ui

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.anurag.notekeepingapp.R
import com.anurag.notekeepingapp.databinding.FragmentEditNoteBinding
import com.anurag.notekeepingapp.utils.KeyboardUtils
import com.anurag.notekeepingapp.viewmodels.EditNoteViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditNoteFragment : Fragment() {
    private var _binding: FragmentEditNoteBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: EditNoteViewModel by viewModels()
    private var _keyboardUtils: KeyboardUtils? = null
    private val keyboardUtils get() = _keyboardUtils!!
    private var showKeyboard: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(
            inflater, container,
            false
        )

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.noteTitleView.setRawInputType(
            InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        )

        binding.bottomBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_delete -> {
                    viewModel.deleteNote()
                    keyboardUtils.hideKeyboard()
                    findNavController().popBackStack()
                    true
                }
                R.id.action_share -> {
                    shareNote(viewModel.noteText)
                    true
                }
                else -> false
            }
        }

        _keyboardUtils = KeyboardUtils(this)
        showKeyboard = showKeyboard ?: (viewModel.id == -1)
        if (showKeyboard as Boolean) binding.noteDescriptionView.let {
            it.requestFocus()
            keyboardUtils.showSoftKeyboard(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_top_edit_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_submit -> {
                viewModel.submitNote()
                keyboardUtils.hideKeyboard()
                true
            }
//            R.id.action_undo -> {
//                binding.noteTitleView.text = recentTitles?.
//            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onStop() {
        super.onStop()
        viewModel.submitNote()  // We must do updating part here because
        // onDestroy and onDestroyView are not called when app is killed.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _keyboardUtils = null
        showKeyboard = null
    }

    private fun shareNote(note: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, note.trim())
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}