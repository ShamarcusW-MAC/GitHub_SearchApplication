package com.example.github_searchapplication.network

import com.example.github_searchapplication.model.Repository
import com.example.github_searchapplication.model.SoleUser
import com.example.github_searchapplication.model.Users
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GitHubFactory {
    val URL_BASE = "https://api.github.com"

    var gitHubService: GitHubService

    init {
        gitHubService = createService(retrofitInstance())
    }


    private fun retrofitInstance(): Retrofit{
        return Retrofit.Builder().baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }


    private fun createService(retrofit: Retrofit): GitHubService{
        return retrofit.create(GitHubService::class.java)
    }

    fun getUsers(userName: String): Observable<Users>{
        return gitHubService.getUsers(userName)

    }

    fun getSoleUser(userName: String): Observable<SoleUser>{
        return gitHubService.getSoleUser(userName)
    }
    fun getRepositories(userName: String): Observable<ArrayList<Repository>>{
        return gitHubService.getRepositories(userName)
    }
}