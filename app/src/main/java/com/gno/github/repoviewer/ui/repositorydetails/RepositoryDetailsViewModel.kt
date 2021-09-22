package com.gno.github.repoviewer.ui.repositorydetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class RepositoryDetailsViewModel(private val url: String) : ViewModel() {

    val urlLiveData = liveData {
        emit(url)
    }

    class RepositoryDetailsViewModelFactory @AssistedInject constructor(
        @Assisted private val url: String,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == RepositoryDetailsViewModel::class.java)
            return RepositoryDetailsViewModel(url) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(
                @Assisted url: String
            ): RepositoryDetailsViewModelFactory
        }
    }
}

