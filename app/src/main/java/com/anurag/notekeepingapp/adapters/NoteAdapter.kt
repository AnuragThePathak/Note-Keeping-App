package com.anurag.notekeepingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anurag.notekeepingapp.data.Note
import com.anurag.notekeepingapp.databinding.ListItemNoteBinding
import com.anurag.notekeepingapp.ui.NoteListFragmentDirections

class NoteAdapter :
    ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallBack) {

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
        holder.bind(item)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    inner class NoteViewHolder(
        private val binding: ListItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.noteCard.setOnClickListener {
                navigateToNote(getItem(bindingAdapterPosition), it)
            }
        }

        fun bind(item: Note) {
            binding.titleView.apply {
                text = item.title
                visibility = if (item.title != "") View.VISIBLE else View.GONE
            }

            binding.descriptionView.apply {
                text = item.description
                visibility = if (item.description != "") View.VISIBLE else View.GONE
            }
        }

        private fun navigateToNote(item: Note, view: View) {
            val direction = NoteListFragmentDirections
                .actionNoteListDestToEditNoteDest(item.id)

            view.findNavController().navigate(direction)
        }

    }
}

private object NoteDiffCallBack : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
        oldItem == newItem
}