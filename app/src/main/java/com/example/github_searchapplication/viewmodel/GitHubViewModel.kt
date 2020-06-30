package com.example.github_searchapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.github_searchapplication.model.Repository
import com.example.github_searchapplication.model.SoleUser
import com.example.github_searchapplication.model.Users
import com.example.github_searchapplication.network.GitHubFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GitHubViewModel(application: Application) : AndroidViewModel(application){
    private val gitHubFactory: GitHubFactory = GitHubFactory()
    private val compositeDisposable = CompositeDisposable()
    val usersInfo = MutableLiveData<Users>()
    val repositoryInfo = MutableLiveData<ArrayList<Repository>>()

    //Displays users based on the user input in the edit text
    fun onUserNameTextChanged(currentInput: CharSequence,start: Int,before : Int,
                              count :Int){
        val currentUserNameEntered = currentInput.toString()
        findUsers(currentUserNameEntered)
    }

    //This method will handle the rest call
    fun findUsers(userName : String){
        compositeDisposable.add(
            getUsers(userName)
                .subscribe({ users ->
                    if(userName.isBlank())
                    {
                        usersInfo.postValue(users)
                    }
                    else {
                        usersInfo.postValue(users)
                    }
                }, { throwable ->
                    Log.e("TAG_ERROR", throwable.message.toString())
                })

        )
    }

    //This method will handle the rest call
    fun findRepositories(userName : String){
        compositeDisposable.add(
            getRepositories(userName)
                .subscribe({ repositories ->

                    repositoryInfo.postValue(repositories)

                }, { throwable ->
                    Log.e("TAG_ERROR", throwable.message.toString())
                })

        )
    }



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

    fun getRepositories(userName: String): Observable<ArrayList<Repository>>{
        return gitHubFactory.getRepositories(userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}