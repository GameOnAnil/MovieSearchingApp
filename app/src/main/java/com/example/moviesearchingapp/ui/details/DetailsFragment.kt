package com.example.moviesearchingapp.ui.details

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.moviesearchingapp.R
import com.example.moviesearchingapp.adapter.CastAdapter
import com.example.moviesearchingapp.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var castAdapter: CastAdapter

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

        castAdapter = CastAdapter()
        detailsViewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)

        binding.recyclerCast.adapter = castAdapter

        Log.d(TAG, "onCreateView: movieId: ${currentMovie.id}")
        detailsViewModel.getCast(currentMovie.id)
        detailsViewModel.castDetail.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onCreateView: $it")
            castAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            castAdapter.notifyDataSetChanged()

        })

        detailsViewModel.getCrew(currentMovie.id)
        detailsViewModel.crewDetail.observe(viewLifecycleOwner, Observer {
            val director = it.filter { crewValue ->
                crewValue.department == "Directing"
            }
            val writers = it.filter { crewValue ->
                crewValue.department == "Writing"
            }
            Log.d(TAG, "onCreateView: director size: ${director.size}")

            if (director.size > 1) {
                var fullDirectors = ""
                for (index in director.indices) {
                    if (index != director.lastIndex) {
                        fullDirectors += "${director[index].name} , "


                    } else {
                        fullDirectors += director[index].name
                    }
                }
                binding.detailsDirectorNames.text = fullDirectors
            } else {
                binding.detailsDirectorNames.text = director[0].name
            }

            if (writers.size > 1) {
                var fullWriter: String = ""
                for (index in writers.indices) {
                    fullWriter += if (index != writers.lastIndex) {
                        "${writers[index].name} , "
                    } else {
                        writers[index].name
                    }
                }
                binding.detailsWriterNames.text = fullWriter
            } else {
                binding.detailsWriterNames.text = writers[0].name
            }
        })


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
                            detailsCrewText.visibility = VISIBLE
                            recyclerCast.visibility = VISIBLE
                            directorText.visibility = VISIBLE
                            detailsDirectorNames.visibility = VISIBLE
                            writerText.visibility = VISIBLE
                            detailsWriterNames.visibility = VISIBLE

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
                detailsCrewText.visibility = VISIBLE
                recyclerCast.visibility = VISIBLE
                directorText.visibility = VISIBLE
                detailsDirectorNames.visibility = VISIBLE
                writerText.visibility = VISIBLE
                detailsWriterNames.visibility = VISIBLE

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