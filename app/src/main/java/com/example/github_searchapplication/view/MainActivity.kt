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
    //Any logic or code not directly involved with the view should be in the viewmodel.
    //private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //With MVVM, you need to use the DataBinding utility to set the content view
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(GitHubViewModel::class.java)
        //assign the viewmodel to the views binding
        binding.viewmodel = viewModel
        //There is no need for this when using MVVM
        //var userName: EditText = findViewById(R.id.username_edittext)
        //We need to observe for changes in the LiveData object and take action once received
        viewModel.usersInfo.observe(this, Observer<Users> { user ->
            displayUsers(user.items!!)
        })

// This should be handled in the viewModel
//        username_edittext.addTextChangedListener(object : TextWatcher{
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                compositeDisposable.add(
//                    viewModel.getUsers(userName.text.toString())
//                        .subscribe({ users ->
//
//
//                            if(userName.text.toString() == "")
//                            {
//                                users.totalCount = 0
//                                displayUsers(users.items!!)
//                            }
//                            else {
//                                displayUsers(users.items!!)
//                            }
//
//
//                        }, { throwable ->
//                            Log.d("TAG_ERROR", throwable.message.toString())
//                        })
//
//                )
//            }
//
//        })



    }

    //Check and see if the adapter is initialized, if not set up the RV.  If so send new list
    // to the RV.  The updateUserList has the notifyOfDataSetChanged
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

