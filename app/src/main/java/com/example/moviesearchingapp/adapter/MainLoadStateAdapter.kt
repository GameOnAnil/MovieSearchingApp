package com.example.moviesearchingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearchingapp.databinding.AdapterLoadStateFooterBinding

class MainLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MainLoadStateAdapter.UpcomingLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): UpcomingLoadStateViewHolder {
        val view = AdapterLoadStateFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UpcomingLoadStateViewHolder(view)
    }


    override fun onBindViewHolder(holder: UpcomingLoadStateViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }


    inner class UpcomingLoadStateViewHolder(private val binding: AdapterLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bindTo(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState !is LoadState.Loading
                textViewError.isVisible = loadState !is LoadState.Loading
            }
        }

    }


}