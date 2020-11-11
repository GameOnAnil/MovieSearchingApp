package com.example.moviesearchingapp.ui.search

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.moviesearchingapp.R
import com.example.moviesearchingapp.adapter.MainLoadStateAdapter
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

    companion object {
        private const val TAG = "SearchFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(layoutInflater)

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        searchAdapter = SearchAdapter(this)

        initAdapter()

        searchViewModel.searchedMovies.observe(viewLifecycleOwner, Observer {
            searchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun initAdapter() {
        binding.apply {
            recyclerViewSearch.adapter = searchAdapter.withLoadStateHeaderAndFooter(
                header = MainLoadStateAdapter { searchAdapter.retry() },
                footer = MainLoadStateAdapter { searchAdapter.retry() }
            )

            searchAdapter.addLoadStateListener { loadState ->
                // Only show the list if refresh succeeds.
                binding.recyclerViewSearch.isVisible =
                    loadState.source.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                //   binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

                // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Log.d(TAG, "initAdapter: error: ${it.error}")
                    Toast.makeText(
                        activity,
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
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