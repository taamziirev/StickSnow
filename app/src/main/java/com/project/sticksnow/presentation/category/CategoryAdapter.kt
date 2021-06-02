package com.project.sticksnow.presentation.category

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.sticksnow.databinding.ItemRecyclerBinding
import com.project.sticksnow.entities.CategoryWithStatus


class CategoryAdapter(
    private val context: Context,
    private val listener: ActionClickListener
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val categories: ArrayList<CategoryWithStatus> = arrayListOf()

    inner class CategoryViewHolder(val binding: ItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryWithStatus) {
            binding.apply {
                textView.text = category.name
            }
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentCategory = categories[position]

        holder.binding.apply {
            textView.text = currentCategory.name
        }

        holder.binding.cbCategory.apply {
            setOnClickListener {
                holder.binding.apply {
                    if (isChecked) {
                        textView.paintFlags =
                            textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                        holder.binding.cbCategory.setOnClickListener {
                            listener.category(currentCategory)
                        }
                    } else {
                        textView.paintFlags =
                            textView.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
                        holder.binding.cbCategory.setOnClickListener {
                            listener.unCategory(currentCategory)
                        }
                    }
                }
            }
        }
    }

    fun submitUpdate(update: List<CategoryWithStatus>) {
        val callback = CategoriesDiffCallback(categories, update)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(callback)
        categories.clear()
        categories.addAll(update)
        diffResult.dispatchUpdatesTo(this)
    }

    class CategoriesDiffCallback(
        private val oldBooks: List<CategoryWithStatus>,
        private val newBooks: List<CategoryWithStatus>
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
            return oldBooks[oldItemPosition].checked == newBooks[newItemPosition].checked
        }
    }

    interface ActionClickListener {
        fun category(category: CategoryWithStatus)
        fun unCategory(category: CategoryWithStatus)
    }

}