package com.example.moviesearchingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearchingapp.databinding.MainRecyclerListBinding
import com.example.moviesearchingapp.model.Movie

class MovieAdapter(
    private val movieListener: MovieListener
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movieList: Movie? = null

    fun setMovie(movie: Movie) {
        movieList = movie
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            MainRecyclerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentMovie = movieList
        if (currentMovie != null) {
            holder.bindTo(currentMovie)
        }
    }

    override fun getItemCount(): Int {
        return if (movieList != null) {
            movieList!!.results.size
        } else 0
    }


    inner class MovieViewHolder(private val binding: MainRecyclerListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = movieList
                    if (item != null) {
                        movieListener.onMovieClicked(item, position)
                    }
                }
            }
        }

        fun bindTo(movie: Movie) {

            binding.apply {
                titleText.text = movie.results[adapterPosition].title

                val imageUrl =
                    "https://image.tmdb.org/t/p/w500" + movie.results[adapterPosition].poster_path

                Glide.with(itemView)
                    .load(imageUrl)
                    .into(imageViewMain)

            }
        }

    }

    interface MovieListener {
        fun onMovieClicked(movie: Movie, position: Int)
    }

}