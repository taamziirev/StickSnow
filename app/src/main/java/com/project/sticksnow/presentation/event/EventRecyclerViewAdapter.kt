package com.project.sticksnow.presentation.event

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.project.sticksnow.databinding.EventItemBinding
import com.project.sticksnow.entities.EventInList
import com.project.sticksnow.entities.EventResult

class EventRecyclerViewAdapter(
    private val context: Context,
    private val listener: EventRecyclerViewAdapter.ActionClickListener
) : RecyclerView.Adapter<EventRecyclerViewAdapter.EventViewHolder>() {

    private val events: ArrayList<EventResult> = arrayListOf()

    inner class EventViewHolder(val binding: EventItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventResult) {
            binding.apply {
                name.text = event.description
                title.text = event.title
            }
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            EventItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.binding.apply {
            title.text = event.title
            name.text = event.description
        }
    }

    fun submitUpdate(update: List<EventResult>) {
        val callback = EventsDiffCallback(events, update)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(callback)
        events.clear()
        events.addAll(update)
        diffResult.dispatchUpdatesTo(this)
    }

    class EventsDiffCallback(
        private val oldBooks: List<EventResult>,
        private val newBooks: List<EventResult>
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldBooks.size
        }

        override fun getNewListSize(): Int {
            return newBooks.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldBooks[oldItemPosition].id == newBooks[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldBooks[oldItemPosition].id == newBooks[newItemPosition].id
        }
    }

    interface ActionClickListener {
        fun event(event: EventInList)
        fun unEvent(event: EventInList)
    }

}