package com.example.github_searchapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.github_searchapplication.R
import com.example.github_searchapplication.adapter.RepositoryAdapter
import com.example.github_searchapplication.model.Repository
import com.example.github_searchapplication.viewmodel.GitHubViewModel
import com.example.github_searchapplication.databinding.ActivityUserProfileBinding
import com.example.github_searchapplication.model.SoleUser
import com.example.github_searchapplication.model.Users
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_user__profile.*

class User_Profile : AppCompatActivity() {

    private lateinit var viewModel: GitHubViewModel
    private lateinit var binding : ActivityUserProfileBinding
    private lateinit var adapter : RepositoryAdapter

//    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityUserProfileBinding>(this, R.layout.activity_user__profile)
        viewModel = ViewModelProviders.of(this).get(GitHubViewModel::class.java)
        //assign the viewmodel to the views binding
        binding.viewmodel = viewModel


//        val intent = intent
//
//        val username: String = intent.getStringExtra("username")
//        val avatarUrl : String = intent.getStringExtra("avatar")
//
//
//        viewModel.findSoleUser(username)
//        viewModel.findRepositories(username)

//        findViewById<TextView>(R.id.usernameinfo_textview).text = username
//        Glide.with(this).load(avatarUrl).into(userprofile_imageview)

//        viewModel = ViewModelProviders.of(this)
//            .get(GitHubViewModel::class.java)

//        compositeDisposable.add(
//            viewModel.getSoleUser(username)
//                .subscribe({ user ->
//                    findViewById<TextView>(R.id.useremailinfo_textview).text = user.email
//                    findViewById<TextView>(R.id.userlocationinfo_textview).text = user.location
//                    findViewById<TextView>(R.id.userjoininfo_textview).text = "Joined " + user.createdAt
//                    findViewById<TextView>(R.id.userfollowersinfo_textview).text = user.followers.toString() + " Followers"
//                    findViewById<TextView>(R.id.userfollowinginfo_textview).text = "Following " + user.following.toString()
//                    findViewById<TextView>(R.id.userbio_textview).text = user.bio
//
//                }, { throwable ->
//                    Log.d("TAG_ERROR", throwable.message.toString())
//                })
//
//        )


        viewModel.soleUserInfo.observe(this, Observer<SoleUser> {soleUser ->


        })

        viewModel.repositoryInfo.observe(this, Observer<List<Repository>> { repositories ->
            displayRepositories(repositories)
        })

//        compositeDisposable.add(
//            viewModel.getRepositories(username)
//                .subscribe({repositories ->
//
//                    displayRepositories(repositories)
//
//                }, {throwable ->
//                    Log.d("TAG_ERROR2", throwable.message.toString())
//
//                })
//        )
//        compositeDisposable
    }

    private fun displayRepositories(repositories: List<Repository>){
        if(::adapter.isInitialized) {
            adapter.updateRepositoryList(repositories)
        } else {
            adapter = RepositoryAdapter(repositories)
            val linear = LinearLayoutManager(this)
            repository_recyclerview.layoutManager = linear
            repository_recyclerview.adapter = adapter
        }
    }
}
