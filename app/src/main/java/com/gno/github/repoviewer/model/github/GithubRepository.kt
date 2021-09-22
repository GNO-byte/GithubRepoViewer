package com.gno.github.repoviewer.model.github

data class GithubRepository(
    val id: Int?,
    val name: String?,
    val owner: GithubOwner?,
    val description: String?,
    val avatar_url: String?,
    val html_url: String?
)