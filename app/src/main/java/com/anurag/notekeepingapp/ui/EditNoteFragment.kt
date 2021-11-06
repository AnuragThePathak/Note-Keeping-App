package com.anurag.notekeepingapp.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anurag.notekeepingapp.databinding.FragmentEditNoteBinding
import com.anurag.notekeepingapp.viewmodels.EditNoteViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditNoteFragment : Fragment() {
    private var _binding: FragmentEditNoteBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: EditNoteViewModel by viewModels()

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

        binding.noteDescriptionView.let {
            it.requestFocus()
            showSoftKeyboard(it)
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.submitNote()  // We must do updating part here because
        // onDestroy and onDestroyView are not called when app is killed.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showSoftKeyboard(editText: EditText) {
        (editText.context.getSystemService(INPUT_METHOD_SERVICE)
                as InputMethodManager)
            .showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

}