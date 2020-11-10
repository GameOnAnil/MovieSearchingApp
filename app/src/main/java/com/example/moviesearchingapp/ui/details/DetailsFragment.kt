package com.example.moviesearchingapp.ui.details

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.moviesearchingapp.R
import com.example.moviesearchingapp.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.*

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "DetailsFragment"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(layoutInflater)

        val args = DetailsFragmentArgs.fromBundle(requireArguments())
        val currentMovie = args.movie

        binding.apply {
            if (currentMovie.backdrop_path != null) {
                val imageUrl =
                    "https://image.tmdb.org/t/p/w500" + currentMovie.backdrop_path

                Glide.with(this@DetailsFragment)
                    .load(imageUrl)
                    .optionalCenterCrop()
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBarDetails.visibility = GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            detailImage.visibility = VISIBLE
                            detailDescription.visibility = VISIBLE
                            detailTitle.visibility = VISIBLE
                            detailsRating.visibility = VISIBLE
                            detailsReleaseDate.visibility = VISIBLE
                            detailsSynopsisText.visibility = VISIBLE
                            ratingText.visibility = VISIBLE
                            progressBarDetails.visibility = GONE



                            return false
                        }
                    })
                    .into(binding.detailImage)
            } else {
                detailImage.visibility = VISIBLE
                detailDescription.visibility = VISIBLE
                detailTitle.visibility = VISIBLE
                detailsRating.visibility = VISIBLE
                detailsReleaseDate.visibility = VISIBLE
                detailsSynopsisText.visibility = VISIBLE
                ratingText.visibility = VISIBLE
                progressBarDetails.visibility = GONE
            }
            detailsReleaseDate.text = currentMovie.release_date
            detailTitle.text = currentMovie.title
            detailDescription.text = currentMovie.overview
            detailsRating.text = currentMovie.vote_average.toString()
        }



        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}