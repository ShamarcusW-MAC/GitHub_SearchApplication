package com.example.github_searchapplication.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
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
import kotlinx.android.synthetic.main.activity_user_profile.*






class UserProfile : AppCompatActivity() {

    private lateinit var viewModel: GitHubViewModel
    private lateinit var binding : ActivityUserProfileBinding
    private lateinit var adapter : RepositoryAdapter

    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile)
        viewModel = ViewModelProviders.of(this).get(GitHubViewModel::class.java)
        //assign the viewmodel to the views binding
        binding.viewmodel = viewModel


        val intent = intent

        val username: String = intent.getStringExtra("username")
        val avatar : String = intent.getStringExtra("avatar")


        Glide.with(this)
            .load(avatar)
            .into(userprofile_imageview)

        viewModel.getSoleUser(username).subscribe { user ->
            findViewById<TextView>(R.id.usernameinfo_textview).text = user.login
            findViewById<TextView>(R.id.userlocationinfo_textview).text = user.location
            findViewById<TextView>(R.id.userjoininfo_textview).text = "Joined " + user.createdAt
            findViewById<TextView>(R.id.userfollowersinfo_textview).text = user.followers.toString() + " Followers"
            findViewById<TextView>(R.id.userfollowinginfo_textview).text = "Following " + user.following.toString()
            findViewById<TextView>(R.id.userbio_textview).text = user.bio
        }

//        viewModel.userAvatar.observe(this, Observer {avatarUrl ->
//
//            Log.d("TAG_URL", avatarUrl)

//        })

//        findViewById<TextView>(R.id.usernameinfo_textview).text = username
//        Glide.with(this).load(avatarUrl).into(userprofile_imageview)

//        viewModel = ViewModelProviders.of(this)
//            .get(GitHubViewModel::class.java)
//

//        compositeDisposable.add(
//            viewModel.getSoleUser(username)
//                .subscribe({ user ->
//                    findViewById<TextView>(R.id.usernameinfo_textview).text = user.login
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
//        )

//        viewModel.soleUserInfo.observe(this, Observer<SoleUser> {soleUser ->
//            findViewById<TextView>(R.id.useremailinfo_textview).text = soleUser.email
//            findViewById<TextView>(R.id.userlocationinfo_textview).text = soleUser.location
//            findViewById<TextView>(R.id.userjoininfo_textview).text = "Joined " + soleUser.createdAt
//            findViewById<TextView>(R.id.userfollowersinfo_textview).text = soleUser.followers.toString() + " Followers"
//            findViewById<TextView>(R.id.userfollowinginfo_textview).text = "Following " + soleUser.following.toString()
//            findViewById<TextView>(R.id.userbio_textview).text = soleUser.bio
//
//        })

        viewModel.findRepositories(username)
        viewModel.repositoryInfo.observe(this, Observer<ArrayList<Repository>> { repositories ->
            displayRepositories(repositories)
            adapter = RepositoryAdapter(repositories)

            repository_edittext.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                    val filteredList : ArrayList<Repository> = ArrayList()

                    for(item: Repository in repositories) {
                        if(item.name.toString().toLowerCase().startsWith(s.toString().toLowerCase())) {
                            filteredList.add(item)
                        }
                    }

                    adapter.updateRepositoryList(filteredList)
                    adapter.notifyDataSetChanged()

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


                }
            })

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


//    private fun filter(text: String, repoList : List<Repository>) {
//        var filteredRepos : List<Repository>
//        for (s in repoList) {
//            //if the existing elements contains the search input
//            if (s.name.toString().toLowerCase().contains(text.toLowerCase())) {
//                //adding the element to filtered list
//                displayRepositories(repoList)
//                }
//        }
//
//    }


    private fun displayRepositories(repositories: ArrayList<Repository>){
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
