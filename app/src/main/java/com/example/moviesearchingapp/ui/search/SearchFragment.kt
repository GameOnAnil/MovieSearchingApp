package com.example.moviesearchingapp.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moviesearchingapp.R
import com.example.moviesearchingapp.adapter.SearchAdapter
import com.example.moviesearchingapp.databinding.FragmentSearchBinding
import com.example.moviesearchingapp.model.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchAdapter.MovieListener {
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!


    private lateinit var searchAdapter: SearchAdapter
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(layoutInflater)

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        searchAdapter = SearchAdapter(this)

        binding.recyclerViewSearch.adapter = searchAdapter

        searchViewModel.searchedMovies.observe(viewLifecycleOwner, Observer {
            searchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.recyclerViewSearch.scrollToPosition(0)
                    searchViewModel.searchWithQuery(query)
                    searchView.clearFocus()
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onMovieClicked(movie: Movie, position: Int) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(movie, position)
        findNavController().navigate(action)

    }


}