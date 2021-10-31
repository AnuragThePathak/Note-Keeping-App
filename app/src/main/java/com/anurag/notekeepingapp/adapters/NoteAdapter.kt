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


class NoteAdapter(private val onClick: (Note) -> Unit) :
    ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            NoteViewHolder {

        return NoteViewHolder(
            ListItemNoteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClick
        )

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class NoteViewHolder(
        private val binding: ListItemNoteBinding,
        private val onClick: (Note) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                navigateToNote(adapterPosition, it)
            }
        }

        private fun navigateToNote(position: Int, view: View) {
            val direction = NoteListFragmentDirections
                .actionNoteListDestToEditNoteDest(getItem(position).id)

            view.findNavController().navigate(direction)
        }

        fun bind(item: Note) {
            binding.apply {
                noteView.text = item.note
                deleteButton.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }

}

private object NoteDiffCallBack : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
        oldItem == newItem
}