package com.example.moviesearchingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearchingapp.R
import com.example.moviesearchingapp.databinding.RecyclerMainListBinding
import com.example.moviesearchingapp.model.Movie

class PopularAdapter(
    private val popularListener: PopularListener
) : PagingDataAdapter<Movie, PopularAdapter.MovieViewHolder>(MOVIE_DIFF_UTIL) {

    companion object {
        val MOVIE_DIFF_UTIL = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            RecyclerMainListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentMovie = getItem(position)
        if (currentMovie != null) {
            holder.bindTo(currentMovie)
        }
    }

    inner class MovieViewHolder(private val binding: RecyclerMainListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        popularListener.onPopularMovieClicked(item, position)
                    }
                }
            }
        }

        fun bindTo(movie: Movie) {
            binding.apply {
                val imageUrl = "https://image.tmdb.org/t/p/w500" + movie.poster_path
                titleText.text = movie.title
                textReleaseDate.text = movie.release_date
                textRating.text = movie.vote_average.toString()
                if (movie.poster_path != null) {
                    Glide.with(itemView)
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .into(imageView)
                }

            }
        }

    }

    interface PopularListener {
        fun onPopularMovieClicked(movie: Movie, position: Int)
    }

}