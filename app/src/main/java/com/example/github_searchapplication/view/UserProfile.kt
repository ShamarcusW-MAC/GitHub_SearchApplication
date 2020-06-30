package com.example.github_searchapplication.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfile : AppCompatActivity() {

    private lateinit var viewModel: GitHubViewModel
    private lateinit var binding : ActivityUserProfileBinding
    private lateinit var adapter : RepositoryAdapter

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile)
        viewModel = ViewModelProviders.of(this).get(GitHubViewModel::class.java)
        //assign the viewmodel to the views binding
        binding.viewmodel = viewModel


        val intent = intent

        //Data is received from the users adapter
        val username: String = intent.getStringExtra("username")
        val avatar : String = intent.getStringExtra("avatar")

        //Image of user's avatar will be loaded within the view
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
        viewModel.findRepositories(username)

        //Repositories of the current user are displayed and updated based on what the user
        //input in the edit text
        viewModel.repositoryInfo.observe(this, Observer<ArrayList<Repository>> { repositories ->

            displayRepositories(repositories)
            repository_edittext.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val filteredList : ArrayList<Repository> = ArrayList()
                    for(item: Repository in repositories) {
                        if(item.name.toString().toLowerCase().startsWith(s.toString().toLowerCase())) {
                            filteredList.add(item)
                        }
                    }
                    adapter.updateRepositoryList(filteredList)
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        })

    }

    //Check if the adapter is initialized, if not, the RV is set up.
    // If so, a new list is sent.
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
