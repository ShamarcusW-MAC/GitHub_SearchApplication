package com.example.github_searchapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.github_searchapplication.model.Repository
import com.example.github_searchapplication.model.SoleUser
import com.example.github_searchapplication.model.Users
import com.example.github_searchapplication.network.GitHubFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GitHubViewModel : ViewModel(){
    private val gitHubFactory: GitHubFactory = GitHubFactory()

    fun getUsers(userName: String): Observable<Users>{
        return gitHubFactory.getUsers(userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun getSoleUser(userName: String): Observable<SoleUser>{
        return gitHubFactory.getSoleUser(userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun getRepositories(userName: String): Observable<List<Repository>>{
        return gitHubFactory.getRepositories(userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}