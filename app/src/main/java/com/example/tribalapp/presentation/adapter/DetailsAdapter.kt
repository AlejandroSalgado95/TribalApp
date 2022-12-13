package com.example.tribalapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.tribalapp.databinding.DetailsItemBinding
import com.example.tribalapp.databinding.ListItemBinding
import com.example.tribalapp.domain.model.JokeModel
import javax.inject.Inject

class DetailsAdapter @Inject constructor(
    private val glide : RequestManager
): ListAdapter<JokeModel, DetailsAdapter.JokeViewHolder>(JokeModelDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return JokeViewHolder(DetailsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class JokeViewHolder(private val binding: DetailsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: JokeModel) = with(binding) {
            jokeText.text = item.value
            glide.load(item.icon_url).transition(
                DrawableTransitionOptions.withCrossFade()).into(jokeIcon)

        }
    }
}

object JokeModelDiff: DiffUtil.ItemCallback<JokeModel>(){
    override fun areContentsTheSame(oldItem: JokeModel, newItem: JokeModel): Boolean {
        return oldItem == newItem
    }
    override fun areItemsTheSame(oldItem: JokeModel, newItem: JokeModel): Boolean {
        return oldItem.id.equals(newItem.id)
    }
}