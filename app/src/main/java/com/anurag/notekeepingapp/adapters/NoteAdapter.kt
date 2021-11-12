package com.anurag.notekeepingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anurag.notekeepingapp.data.Note
import com.anurag.notekeepingapp.databinding.ListItemNoteBinding
import com.anurag.notekeepingapp.recyclerviewselection.NoteItemDetails
import com.anurag.notekeepingapp.ui.NoteListFragmentDirections

class NoteAdapter :
    ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallBack) {

    var selectionTracker: SelectionTracker<Long>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            NoteViewHolder {

        return NoteViewHolder(
            ListItemNoteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, selectionTracker?.isSelected(item.id.toLong()))
    }

    inner class NoteViewHolder(
        private val binding: ListItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                navigateToNote(getItem(adapterPosition), it)
            }
        }

        fun bind(item: Note, isSelected: Boolean?) {
            binding.titleView.apply {
                text = item.title
                visibility = if (item.title != "") View.VISIBLE else View.GONE
            }

            binding.descriptionView.apply {
                text = item.description
                visibility = if (item.description != "") View.VISIBLE else View.GONE
            }

            itemView.isActivated = isSelected == true
        }

        private fun navigateToNote(item: Note, view: View) {
            val direction = NoteListFragmentDirections
                .actionNoteListDestToEditNoteDest(item.id)

            view.findNavController().navigate(direction)
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            NoteItemDetails(
                bindingAdapterPosition,
                getItem(bindingAdapterPosition).id.toLong()
            )
    }
}

private object NoteDiffCallBack : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
        oldItem == newItem
}