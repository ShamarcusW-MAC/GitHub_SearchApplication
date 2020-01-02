package com.example.github_searchapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_searchapplication.model.Repository
import com.example.github_searchapplication.model.SoleUser
import com.example.github_searchapplication.model.Users
import com.example.github_searchapplication.network.GitHubFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GitHubViewModel : ViewModel(){
    private val gitHubFactory: GitHubFactory = GitHubFactory()
    private val compositeDisposable = CompositeDisposable()
    val usersInfo = MutableLiveData<Users>()
    val soleUserInfo = MutableLiveData<SoleUser>()
    val repositoryInfo = MutableLiveData<List<Repository>>()

    //TextWatcher Code in MVVM
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
                        //What is this for?
                        //users.totalCount = 0
                        usersInfo.postValue(users)
                        //When this post, we will observe for the change in the view
                        //displayUsers(users.items!!)
                    }
                    else {
                        usersInfo.postValue(users)
                        //displayUsers(users.items!!)
                    }


                }, { throwable ->
                    Log.e("TAG_ERROR", throwable.message.toString())
                })

        )
    }

    //This method will handle the rest call
    fun findSoleUser(userName : String){
        compositeDisposable.add(
            getSoleUser(userName)
                .subscribe({ user ->
                    if(userName.isBlank())
                    {
                        //What is this for?
                        //users.totalCount = 0
                        soleUserInfo.postValue(user)
                        //When this post, we will observe for the change in the view
                        //displayUsers(users.items!!)
                    }
                    else {
                        soleUserInfo.postValue(user)
                        //displayUsers(users.items!!)
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




    //None of this have observers assigned??

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