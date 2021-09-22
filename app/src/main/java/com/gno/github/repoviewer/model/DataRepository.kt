package com.gno.github.repoviewer.model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    val repositoriesPagingSource: RepositoriesPagingSource
)