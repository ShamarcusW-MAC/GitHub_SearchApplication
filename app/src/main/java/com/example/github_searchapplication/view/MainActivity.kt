package com.example.github_searchapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_searchapplication.R
import com.example.github_searchapplication.adapter.UsersAdapter
import com.example.github_searchapplication.model.Item
import com.example.github_searchapplication.viewmodel.GitHubViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: GitHubViewModel

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var userName: EditText = findViewById(R.id.username_edittext)

        viewModel = ViewModelProviders.of(this)
            .get(GitHubViewModel::class.java)

//        search_button.setOnClickListener {
//            compositeDisposable.add(
//                viewModel.getUsers(userName.text.toString())
//                    .subscribe({ users ->
//
//                        displayUsers(users.items)
//                        Toast.makeText(this, "Works!", Toast.LENGTH_LONG).show()
//
//
//                    }, { throwable ->
//                        Log.d("TAG_ERROR", throwable.message.toString())
//                    })
//
//            )
//        }

//        val intent = Intent(this, User_Profile::class.java)
//        username_recyclerview.setOnClickListener {
//            intent.putExtra("username", )
//            startActivity(intent)
//        }

        username_edittext.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                compositeDisposable.add(
                    viewModel.getUsers(userName.text.toString())
                        .subscribe({ users ->


                            if(userName.text.toString() == "")
                            {
                                users.totalCount = 0
                                displayUsers(users.items)
                            }
                            else {
                                displayUsers(users.items)
                            }


//                            Toast.makeText(this, "Works!", Toast.LENGTH_LONG).show()


                        }, { throwable ->
                            Log.d("TAG_ERROR", throwable.message.toString())
                        })

                )
            }

        })



    }

    private fun displayUsers(users: List<Item>){
        val adapter = UsersAdapter(users)
        username_recyclerview.adapter = adapter
        adapter.notifyDataSetChanged()
        val linear = LinearLayoutManager(this)

        username_recyclerview.layoutManager = linear

    }
}
