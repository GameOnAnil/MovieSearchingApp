package com.example.moviesearchingapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moviesearchingapp.R
import com.example.moviesearchingapp.adapter.PopularAdapter
import com.example.moviesearchingapp.adapter.TopRatedAdapter
import com.example.moviesearchingapp.adapter.UpcomingMovieAdapter
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

        binding.apply {
            recyclerViewMainUpcoming.adapter = upcomingMovieAdapter
            recyclerTopRated.adapter = topRatedAdapter
            recyclerPopular.adapter = popularAdapter

            recyclerViewMainUpcoming.setHasFixedSize(true)
            recyclerTopRated.setHasFixedSize(true)
            recyclerPopular.setHasFixedSize(true)
        }



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

        setHasOptionsMenu(true)

        return binding.root
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