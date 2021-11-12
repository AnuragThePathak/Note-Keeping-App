package com.anurag.notekeepingapp.recyclerviewselection

import androidx.recyclerview.selection.ItemDetailsLookup

class NoteItemDetails(
    private val position: Int,
    private val selectionKey: Long
) : ItemDetailsLookup.ItemDetails<Long>() {

    override fun getPosition(): Int = position
    override fun getSelectionKey(): Long = selectionKey
}