package com.example.moviesearchingapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearchingapp.R
import com.example.moviesearchingapp.databinding.RecyclerCastListBinding
import com.example.moviesearchingapp.model.Cast


class CastAdapter(
) : PagingDataAdapter<Cast, CastAdapter.MovieViewHolder>(MOVIE_DIFF_UTIL) {

    companion object {
        val MOVIE_DIFF_UTIL = object : DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem.cast_id == newItem.cast_id
            }

            override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            RecyclerCastListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentCast = getItem(position)
        Log.d("TAG", "onBindViewHolder: currentCast: $currentCast")
        if (currentCast != null) {
            holder.bindTo(currentCast)
        }
    }

    inner class MovieViewHolder(private val binding: RecyclerCastListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(cast: Cast) {
            binding.apply {
                val imageUrl = "https://image.tmdb.org/t/p/w500" + cast.profile_path
                textName.text = cast.name
                textCharacter.text = cast.character

                if (cast.profile_path != null) {
                    Glide.with(itemView)
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .into(imageView)
                }

            }
        }

    }


}