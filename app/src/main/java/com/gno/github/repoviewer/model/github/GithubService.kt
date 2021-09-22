package com.gno.github.repoviewer.model.github

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("/repositories")
    suspend fun getRepositories(
        @Query("since") since: Int?,
    ): Response<List<GithubRepository>>


    companion object {

        const val BASE_URL = "https://api.github.com"
        const val MAX_PAGE_SIZE = 100

    }

}