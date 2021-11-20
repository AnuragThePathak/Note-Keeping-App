package com.anurag.notekeepingapp.utils

import java.util.*

class UndoRedoUtil {

    private var recentTitles: Stack<String>? = null
    private var recentDescriptions: Stack<String>? = null

    private var currentTitle: String? = null
    private var currentDescription: String? = null

    private var currentTitleIndex: Int? = -1
    private var currentDescriptionIndex: Int? = -1

    fun xy() {
//        currentTitle = viewModel.note.value?.title
//        currentTitle = viewModel.note.value?.description
    }

    fun onTapUndo() {
//        currentTitle = viewModel.note.value?.title
//        currentDescription = viewModel.note.value?.description

        recentTitles?.add(currentTitle)
        recentDescriptions?.add(currentDescription)
    }
}