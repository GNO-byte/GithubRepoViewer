package com.gno.github.repoviewer.ui.repositories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gno.github.repoviewer.R

class RepositoriesLoaderStateAdapter :
    LoadStateAdapter<RepositoriesLoaderStateAdapter.ItemViewHolder>() {

    override fun getStateViewType(loadState: LoadState) = when (loadState) {
        is LoadState.NotLoading -> error("Not supported")
        LoadState.Loading -> PROGRESS
        is LoadState.Error -> ERROR
    }

    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemViewHolder {
        return when (loadState) {
            LoadState.Loading -> ProgressViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_progress, parent, false)
            )
            is LoadState.Error -> ErrorViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_error, parent, false)
            )
            is LoadState.NotLoading -> error("Not supported")
        }
    }

    private companion object {

        private const val ERROR = 1
        private const val PROGRESS = 0
    }

    abstract class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(loadState: LoadState)
    }

    class ProgressViewHolder(
        view: View
    ) : ItemViewHolder(view) {

        override fun bind(loadState: LoadState) {
            // Do nothing
        }
    }

    class ErrorViewHolder(
        view: View
    ) : ItemViewHolder(view) {

        private val errorMessage: TextView = view.findViewById(R.id.error_message)

        override fun bind(loadState: LoadState) {
            require(loadState is LoadState.Error)
            errorMessage.text = loadState.error.localizedMessage
        }

    }
}