package com.example.moviesearchingapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.filter
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

    companion object {
        private const val TAG = "MainFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater)

        movieAdapter = MovieAdapter(this)

        binding.recyclerViewMainUpcoming.adapter = movieAdapter
        binding.recyclerViewMainUpcoming.setHasFixedSize(true)


        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        movieViewModel.movies.observe(viewLifecycleOwner, Observer {
            movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            movieAdapter.notifyDataSetChanged()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.recyclerViewMainUpcoming.scrollToPosition(0)
                    movieViewModel.searchWithQuery(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_popular -> {
                Log.d(TAG, "onOptionsItemSelected: latest movie clicked")
                //  movieViewModel.getPopularMovies()
                return true
            }
            R.id.item_top_rated -> {
                Log.d(TAG, "onOptionsItemSelected: Top rated Movies clicked")
                //  movieViewModel.getTopRatedMovies()
                return true
            }
            R.id.item_upcoming_movies -> {
                Log.d(TAG, "onOptionsItemSelected: Upcoming Movies clicked")
                movieViewModel.getUpComingMovies()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

    }


}