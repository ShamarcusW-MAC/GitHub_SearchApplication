package com.example.github_searchapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_searchapplication.R
import com.example.github_searchapplication.adapter.UsersAdapter
import com.example.github_searchapplication.databinding.ActivityMainBinding
import com.example.github_searchapplication.model.Item
import com.example.github_searchapplication.model.Users
import com.example.github_searchapplication.viewmodel.GitHubViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: GitHubViewModel
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(GitHubViewModel::class.java)
        //assign the viewmodel to the views binding
        binding.viewmodel = viewModel
        //Observe for changes in the LiveData object and take action once received
        viewModel.usersInfo.observe(this, Observer<Users> { user ->
            displayUsers(user.items!!)
        })
    }

    //Check if the adapter is initialized, if not, the RV is set up.
    // If so, a new list is sent.
    private fun displayUsers(users: List<Item>){
        if(::adapter.isInitialized) {
            adapter.updateUserList(users)
        } else {
            adapter = UsersAdapter(users)
            val linear = LinearLayoutManager(this)
            username_recyclerview.layoutManager = linear
            username_recyclerview.adapter = adapter
        }


    }
}

