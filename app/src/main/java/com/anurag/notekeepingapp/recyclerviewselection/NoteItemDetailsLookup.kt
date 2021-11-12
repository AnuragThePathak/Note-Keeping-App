package com.anurag.notekeepingapp.recyclerviewselection

import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.anurag.notekeepingapp.adapters.NoteAdapter

class NoteItemDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<Long>() {

    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        val view: View? = recyclerView.findChildViewUnder(e.x, e.y)
        if (view != null) {
            val viewHolder = recyclerView.getChildViewHolder(view)
            if (viewHolder is NoteAdapter.NoteViewHolder) {
                return viewHolder.getItemDetails()
            }
        }
        return null
    }
}