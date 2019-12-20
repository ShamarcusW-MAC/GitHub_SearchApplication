package com.example.github_searchapplication.network

import com.example.github_searchapplication.model.Repository
import com.example.github_searchapplication.model.SoleUser
import com.example.github_searchapplication.model.Users
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {

    @GET("/search/users")
    fun getUsers(@Query("q")username: String): Observable<Users>

    @GET ("users/{user_name}")
    fun getSoleUser(@Path("user_name")username: String) : Observable<SoleUser>

    @GET("users/{user_name}/repos")
    fun getRepositories(@Path("user_name") username: String): Observable<List<Repository>>

}