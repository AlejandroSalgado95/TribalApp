package com.example.tribalapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.tribalapp.databinding.ListItemBinding
import com.example.tribalapp.domain.model.JokeModel
import javax.inject.Inject


class listAdapter(
): ListAdapter<String, listAdapter.CategoryViewHolder>(StringDiff) {

    private var onItemClickListener: ((String, Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (String, Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CategoryViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) = with(binding) {
            categoryText.text = item
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(item,adapterPosition)
            }
        }
    }
}

object StringDiff: DiffUtil.ItemCallback<String>(){
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem.equals(newItem)
    }
}