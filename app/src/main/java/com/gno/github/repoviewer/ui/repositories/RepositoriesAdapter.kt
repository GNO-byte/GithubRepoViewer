package com.gno.github.repoviewer.ui.repositories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gno.github.repoviewer.R
import com.gno.github.repoviewer.model.github.GithubRepository

class RepositoriesAdapter(private val cellClickListener: (String) -> Unit) :
    PagingDataAdapter<GithubRepository, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<GithubRepository>() {
            override fun areItemsTheSame(
                oldItem: GithubRepository,
                newItem: GithubRepository
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: GithubRepository,
                newItem: GithubRepository
            ): Boolean =
                oldItem.name == newItem.name &&
                        oldItem.owner == oldItem.owner &&
                        oldItem.description == oldItem.description
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        getItem(position)?.let {
            (holder as? RepositoryViewHolder)?.bind(item = it, cellClickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return RepositoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_repository, parent, false)
        )
    }

    class RepositoryViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {


        private var body: ViewGroup = view.findViewById(R.id.body)
        private var avatar: ImageView = view.findViewById(R.id.avatar)
        private var id = view.findViewById<TextView>(R.id.id)
        private var name: TextView = view.findViewById(R.id.name)
        private var owner: TextView = view.findViewById(R.id.owner)
        private var description: TextView = view.findViewById(R.id.description)

        fun bind(item: GithubRepository, cellClickListener: (String) -> Unit) {

            item.owner?.avatar_url?.let {
                Glide.with(avatar).load(it).placeholder(R.drawable.label).circleCrop().into(avatar)
            }

            item.id?.let { id.text = it.toString() }
            item.name?.let { name.text = it }
            item.owner?.login?.let { owner.text = it }
            item.description?.let { description.text = it }

            body.setOnClickListener {
                item.html_url?.let {
                    cellClickListener.invoke(it)
                }
            }
        }

    }

}