package com.example.moviesearchingapp.ui.details

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
        val currentMovie = args.movie

        binding.apply {
            if (currentMovie.backdrop_path != null) {
                val imageUrl =
                    "https://image.tmdb.org/t/p/w500" + currentMovie.backdrop_path

                Glide.with(this@DetailsFragment)
                    .load(imageUrl)
                    .centerCrop()
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
                            progressBarDetails.visibility = GONE

                            return false
                        }
                    })
                    .into(binding.detailImage)
            } else {
                detailImage.visibility = VISIBLE
                detailDescription.visibility = VISIBLE
                detailTitle.visibility = VISIBLE
                progressBarDetails.visibility = GONE
            }
            detailTitle.text = currentMovie.title
            detailDescription.text = currentMovie.overview
        }



        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}