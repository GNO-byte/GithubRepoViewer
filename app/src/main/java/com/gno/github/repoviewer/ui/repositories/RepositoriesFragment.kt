package com.gno.github.repoviewer.ui.repositories

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.gno.github.repoviewer.R
import com.gno.github.repoviewer.activityComponent
import com.gno.github.repoviewer.databinding.FragmentRepositoriesBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoriesFragment : Fragment() {

    private lateinit var binding: FragmentRepositoriesBinding

    @Inject
    lateinit var viewModelFactory: RepositoriesViewModel.Factory
    private val viewModel: RepositoriesViewModel by viewModels {
        viewModelFactory
    }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        RepositoriesAdapter { url ->
            val bundle = bundleOf("url" to url)
            findNavController().navigate(R.id.nav_repositorydetails, bundle)
        }
    }

    override fun onAttach(context: Context) {
        context.activityComponent.fragmentComponent().fragment(this).build()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRepositoriesBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.repositories.adapter = adapter.withLoadStateHeaderAndFooter(
            header = RepositoriesLoaderStateAdapter(),
            footer = RepositoriesLoaderStateAdapter()
        )

        adapter.addLoadStateListener { state ->
            binding.repositories.isVisible = state.refresh != LoadState.Loading
            binding.progress.isVisible = state.refresh == LoadState.Loading

        }
        viewModel.repositoriesLiveData.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }

    }
}