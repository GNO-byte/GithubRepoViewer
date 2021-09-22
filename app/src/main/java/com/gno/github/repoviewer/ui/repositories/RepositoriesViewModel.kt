package com.gno.github.repoviewer.ui.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gno.github.repoviewer.model.DataRepository
import javax.inject.Inject

class RepositoriesViewModel(private val dataRepository: DataRepository) : ViewModel() {

    val repositoriesLiveData = Pager(PagingConfig(20))
    { dataRepository.repositoriesPagingSource }.flow.cachedIn(viewModelScope).asLiveData()

    class Factory @Inject constructor(
        private val dataRepository: DataRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == RepositoriesViewModel::class.java)
            return RepositoriesViewModel(dataRepository) as T
        }
    }
}