package com.anurag.notekeepingapp.recyclerviewselection

import androidx.recyclerview.selection.ItemKeyProvider
import com.anurag.notekeepingapp.data.Note

class NoteItemKeyProvider(private val items: List<Note>) :
    ItemKeyProvider<Long>(SCOPE_MAPPED) {

    override fun getKey(position: Int): Long = items[position].id.toLong()

    override fun getPosition(key: Long): Int = items.indexOf(items.find {
        it.id == key.toInt()
    })
}