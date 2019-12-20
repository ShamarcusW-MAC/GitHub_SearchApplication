package com.example.github_searchapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.github_searchapplication.R
import com.example.github_searchapplication.adapter.RepositoryAdapter
import com.example.github_searchapplication.model.Repository
import com.example.github_searchapplication.viewmodel.GitHubViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_user__profile.*

class User_Profile : AppCompatActivity() {

    private lateinit var viewModel: GitHubViewModel

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user__profile)

        val intent = intent
//        val user : TextView = findViewById(R.id.usernameinfo_textview)

        val username: String = intent.getStringExtra("username")
        val avatarUrl : String = intent.getStringExtra("avatar")

        findViewById<TextView>(R.id.usernameinfo_textview).text = username
//        user.text = username
        Glide.with(this).load(avatarUrl).into(userprofile_imageview)

        viewModel = ViewModelProviders.of(this)
            .get(GitHubViewModel::class.java)

        compositeDisposable.add(
            viewModel.getSoleUser(username)
                .subscribe({ user ->
                    findViewById<TextView>(R.id.useremailinfo_textview).text = user.email
                    findViewById<TextView>(R.id.userlocationinfo_textview).text = user.location
                    findViewById<TextView>(R.id.userjoininfo_textview).text = "Joined " + user.createdAt
                    findViewById<TextView>(R.id.userfollowersinfo_textview).text = user.followers.toString() + " Followers"
                    findViewById<TextView>(R.id.userfollowinginfo_textview).text = "Following " + user.following.toString()
                    findViewById<TextView>(R.id.userbio_textview).text = user.bio

                }, { throwable ->
                    Log.d("TAG_ERROR", throwable.message.toString())
                })

        )
        compositeDisposable.add(
            viewModel.getRepositories(username)
                .subscribe({repositories ->

                    displayRepositories(repositories)

                }, {throwable ->
                    Log.d("TAG_ERROR2", throwable.message.toString())

                })
        )
        compositeDisposable
    }

    private fun displayRepositories(repositories: List<Repository>){
        val adapter = RepositoryAdapter(repositories)
        repository_recyclerview.adapter = adapter
        val linear = LinearLayoutManager(this)
        repository_recyclerview.layoutManager = linear
    }
}
