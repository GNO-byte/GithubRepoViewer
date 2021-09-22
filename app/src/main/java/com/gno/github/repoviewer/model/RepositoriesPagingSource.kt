package com.gno.github.repoviewer.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gno.github.repoviewer.model.github.GithubRepository
import com.gno.github.repoviewer.model.github.GithubService
import retrofit2.HttpException
import javax.inject.Inject

class RepositoriesPagingSource @Inject constructor(
    private val githubService: GithubService
) : PagingSource<Int, GithubRepository>() {

    private var currentPrevPageSince: Int? = null

    override fun getRefreshKey(state: PagingState<Int, GithubRepository>): Int? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.id
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepository> {

        val pageSince = params.key
        val pageSize = params.loadSize.coerceAtMost(GithubService.MAX_PAGE_SIZE)

        if (pageSince == -1) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        try {

            val response = githubService.getRepositories(pageSince)

            return if (response.isSuccessful) {

                val githubRepositories = response.body()!!.take(pageSize)

                val nextPageSince = githubRepositories.last().id
                val prevPageSince = currentPrevPageSince
                currentPrevPageSince = pageSince

                LoadResult.Page(githubRepositories, prevPageSince, nextPageSince)

            } else {
                LoadResult.Error(HttpException(response))
            }

        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

    }
}