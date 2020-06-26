package com.example.github_searchapplication.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github_searchapplication.R
import com.example.github_searchapplication.model.Item
import com.example.github_searchapplication.model.SoleUser
import com.example.github_searchapplication.view.UserProfile

class UsersAdapter (var usersList: List<Item>): RecyclerView.Adapter<UsersAdapter.UsersViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item_layout, parent, false)
        return UsersViewHolder(view)
    }

    override fun getItemCount(): Int {

        return usersList.size

    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {

        holder.userName.text = usersList[position].login.toString()

//        holder.repoNumber.text = "Repo: " + usersList[position]

        Glide.with(holder.itemView.context)
            .load(usersList[position].avatarUrl)
            .into(holder.userAvatar)
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, UserProfile::class.java)
            intent.putExtra("username", usersList[position].login)
            intent.putExtra("avatar", usersList[position].avatarUrl)
            it.context.startActivity(intent)
        }


    }

    fun updateUserList(updatedList : List<Item>) {
        usersList = updatedList
        notifyDataSetChanged()
    }


    inner class UsersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val userAvatar: ImageView = itemView.findViewById(R.id.user_imageview)

        val userName: TextView = itemView.findViewById(R.id.username_textview)

//        val repoNumber: TextView = itemView.findViewById(R.id.repositoryamount_textview)
    }
}