package com.gno.github.repoviewer.di.component

import androidx.fragment.app.Fragment

import com.gno.github.repoviewer.ui.repositories.RepositoriesFragment
import com.gno.github.repoviewer.ui.repositorydetails.RepositoryDetailsFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
interface FragmentComponent {

    fun inject(repositoriesFragment: RepositoriesFragment)
    fun inject(repositoriesFragment: RepositoryDetailsFragment)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun fragment(fragment: Fragment): Builder

        fun build(): FragmentComponent
    }
}


