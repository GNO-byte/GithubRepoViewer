package com.gno.github.repoviewer.di.module

import com.gno.github.repoviewer.model.github.GithubService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
class NetworkModule {

    @Provides
    fun provideGithubOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor {
                it.proceed(
                    it.request()
                        .newBuilder()
                        .header("accept", "application/vnd.github.v3+json")
                        .build()
                )
            }.build()

    @Provides
    fun provideGithubService(okHttpClient: OkHttpClient): GithubService =
        Retrofit.Builder()
            .baseUrl(GithubService.BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build().create()

}