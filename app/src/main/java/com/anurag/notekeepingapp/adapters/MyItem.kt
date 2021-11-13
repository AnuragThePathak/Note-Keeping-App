package com.anurag.notekeepingapp.adapters

import android.view.View
import androidx.navigation.findNavController
import com.anurag.notekeepingapp.R
import com.anurag.notekeepingapp.data.Note
import com.anurag.notekeepingapp.databinding.ListItemNoteBinding
import com.anurag.notekeepingapp.ui.NoteListFragmentDirections
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder


class MyItem /*(1)*/(private val note: Note) :
    AbstractFlexibleItem<MyItem.MyViewHolder>() {
    /**
     * When an item is equals to another?
     * Write your own concept of equals, mandatory to implement or use
     * default java implementation (return this == o;) if you don't have unique IDs!
     * This will be explained in the "Item interfaces" Wiki page.
     */
    override fun equals(other: Any?): Boolean {
        if (other is MyItem) {
            return note.id == other.note.id
        }
        return false
    }

    /**
     * You should implement also this method if equals() is implemented.
     * This method, if implemented, has several implications that Adapter handles better:
     * - The Hash, increases performance in big list during Update & Filter operations.
     * - You might want to activate stable ids via Constructor for RV, if your id
     * is unique (read more in the wiki page: "Setting Up Advanced") you will benefit
     * of the animations also if notifyDataSetChanged() is invoked.
     */
    override fun hashCode(): Int {
        return note.id.hashCode()
    }

    /**
     * For the item type we need an int value: the layoutResID is sufficient.
     */
    override fun getLayoutRes(): Int {
        return R.layout.list_item_note
    }

    /**
     * Delegates the creation of the ViewHolder to the user (AutoMap).
     * The inflated view is already provided as well as the Adapter.
     */
    override fun createViewHolder(
        view: View?,
        adapter: FlexibleAdapter<IFlexible<*>>
    ): MyViewHolder {
        return MyViewHolder(ListItemNoteBinding.bind(view!!), adapter)
    }

    /**
     * The Adapter and the Payload are provided to perform and get more specific
     * information.
     */
    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<*>>, holder: MyViewHolder,
        position: Int,
        payloads: List<Any>
    ) {

        holder.id = note.id
        // Title appears disabled if item is disabled
        holder.title.apply {
            isEnabled = isEnabled
            text = note.title
            visibility = if (note.title != "") View.VISIBLE else View.GONE
        }

        holder.description.apply {
            text = note.description
            isEnabled = isEnabled
            visibility = if (note.description != "") View.VISIBLE else View.GONE
        }


    }

    /**
     * The ViewHolder used by this item.
     * Extending from FlexibleViewHolder is recommended especially when you will use
     * more advanced features.
     */
    inner class MyViewHolder(
        binding: ListItemNoteBinding,
        adapter: FlexibleAdapter<*>
    ) : FlexibleViewHolder(binding.root, adapter) {
        var title = binding.titleView
        var description = binding.descriptionView
        var id: Int = note.id

        init {
            shouldAddSelectionInActionMode()
            binding.noteCard.setOnClickListener {
                navigateToNote(id, it)
            }
        }

        private fun navigateToNote(id: Int, view: View) {
            val direction = NoteListFragmentDirections
                .actionNoteListDestToEditNoteDest(id)

            view.findNavController().navigate(direction)
        }
    }
}