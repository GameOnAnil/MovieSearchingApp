package com.example.moviesearchingapp.ui.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.moviesearchingapp.R
import com.example.moviesearchingapp.databinding.FragmentDetailsBinding
import com.example.moviesearchingapp.model.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.*
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "DetailsFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(layoutInflater)

        val args = DetailsFragmentArgs.fromBundle(requireArguments())
        val movie = args.movie
        val adapterPosition = args.position
        val currentMovie = movie.results[adapterPosition]

        binding.apply {
            detailTitle.text = currentMovie.title
            detailDescription.text = currentMovie.overview

            val imageUrl =
                "https://image.tmdb.org/t/p/w500" + currentMovie.backdrop_path

            Log.d(TAG, "onCreateView: link:$imageUrl")

            Glide.with(this@DetailsFragment)
                .load(imageUrl)
                .centerCrop()
                .into(binding.detailImage)
        }



        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}