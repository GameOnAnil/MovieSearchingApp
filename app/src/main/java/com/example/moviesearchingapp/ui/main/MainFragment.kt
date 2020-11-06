package com.example.moviesearchingapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearchingapp.R
import com.example.moviesearchingapp.adapter.MovieAdapter
import com.example.moviesearchingapp.databinding.FragmentMainBinding
import com.example.moviesearchingapp.model.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), MovieAdapter.MovieListener {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater)

        movieAdapter = MovieAdapter(this)
        binding.recyclerViewMain.adapter = movieAdapter

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        movieViewModel.searchedMovies.observe(viewLifecycleOwner, Observer {
            movieAdapter.setMovie(it)
            movieAdapter.notifyDataSetChanged()
        })

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


}