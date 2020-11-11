package com.example.moviesearchingapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.moviesearchingapp.adapter.*
import com.example.moviesearchingapp.databinding.FragmentMainBinding
import com.example.moviesearchingapp.model.Movie
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment(), UpcomingMovieAdapter.MovieListener, PopularAdapter.PopularListener,
    TopRatedAdapter.TopRatedListener {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var upcomingMovieAdapter: UpcomingMovieAdapter
    private lateinit var topRatedAdapter: TopRatedAdapter
    private lateinit var popularAdapter: PopularAdapter


    companion object {
        private const val TAG = "MainFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater)

        upcomingMovieAdapter = UpcomingMovieAdapter(this)
        topRatedAdapter = TopRatedAdapter(this)
        popularAdapter = PopularAdapter(this)

        //--------setRecylerView Adapters to recylerView--------
        addRecyclerAdapters()


        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        movieViewModel.upcomingMovies.observe(viewLifecycleOwner, Observer {
            upcomingMovieAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            upcomingMovieAdapter.notifyDataSetChanged()
        })

        movieViewModel.topRatedMovies.observe(viewLifecycleOwner, Observer {
            topRatedAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            topRatedAdapter.notifyDataSetChanged()
        })

        movieViewModel.popularMovies.observe(viewLifecycleOwner, Observer {
            popularAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            popularAdapter.notifyDataSetChanged()
        })

        //-----add load state listeners
        handleLoadStateListeners()


        setHasOptionsMenu(true)

        return binding.root
    }

    private fun addRecyclerAdapters() {
        binding.apply {
            recyclerPopular.adapter = popularAdapter.withLoadStateHeaderAndFooter(
                header = MainLoadStateAdapter { popularAdapter.retry() },
                footer = MainLoadStateAdapter { popularAdapter.retry() }
            )


            recyclerViewMainUpcoming.adapter = upcomingMovieAdapter.withLoadStateHeaderAndFooter(
                header = MainLoadStateAdapter { upcomingMovieAdapter.retry() },
                footer = MainLoadStateAdapter { upcomingMovieAdapter.retry() }
            )

            recyclerTopRated.adapter = topRatedAdapter.withLoadStateHeaderAndFooter(
                header = MainLoadStateAdapter { topRatedAdapter.retry() },
                footer = MainLoadStateAdapter { topRatedAdapter.retry() }
            )


            buttonRetry.setOnClickListener {
                upcomingMovieAdapter.retry()
            }

            buttonRetryPopular.setOnClickListener {
                popularAdapter.retry()
            }

            buttonRetryTop.setOnClickListener {
                topRatedAdapter.retry()
            }

            recyclerViewMainUpcoming.setHasFixedSize(true)
            recyclerTopRated.setHasFixedSize(true)
            recyclerPopular.setHasFixedSize(true)
        }
    }

    private fun handleLoadStateListeners() {
        upcomingMovieAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerViewMainUpcoming.isVisible =
                    loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    upcomingMovieAdapter.itemCount < 1
                ) {
                    recyclerViewMainUpcoming.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }
        //----for popular movies
        popularAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBarPopular.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerPopular.isVisible =
                    loadState.source.refresh is LoadState.NotLoading
                buttonRetryPopular.isVisible = loadState.source.refresh is LoadState.Error
                textViewErrorPopular.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    popularAdapter.itemCount < 1
                ) {
                    recyclerPopular.isVisible = false
                    textViewEmptyPopular.isVisible = true
                } else {
                    textViewEmptyPopular.isVisible = false
                }
            }
        }
        //-----for Top Rated Movies
        topRatedAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBarTop.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerTopRated.isVisible =
                    loadState.source.refresh is LoadState.NotLoading
                buttonRetryTop.isVisible = loadState.source.refresh is LoadState.Error
                textViewErrorTop.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    topRatedAdapter.itemCount < 1
                ) {
                    recyclerTopRated.isVisible = false
                    textViewEmptyTop.isVisible = true
                } else {
                    textViewEmptyTop.isVisible = false
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onMovieClicked(movie: Movie, position: Int) {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(movie, position)
        findNavController().navigate(action)
    }

    override fun onPopularMovieClicked(movie: Movie, position: Int) {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(movie, position)
        findNavController().navigate(action)
    }

    override fun onTopRatedMovieClicked(movie: Movie, position: Int) {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(movie, position)
        findNavController().navigate(action)
    }


}