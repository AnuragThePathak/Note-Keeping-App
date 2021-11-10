package com.anurag.notekeepingapp.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment

class KeyboardUtils(private val fragment: Fragment) {

    private val imm = fragment.requireContext().getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager

    fun hideKeyboard() = fragment.requireActivity().currentFocus?.let {
        it.clearFocus()
        imm.hideSoftInputFromWindow(
            it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    fun showSoftKeyboard(editText: EditText) =
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
}