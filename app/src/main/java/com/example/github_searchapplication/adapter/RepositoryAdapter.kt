package com.example.github_searchapplication.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.github_searchapplication.R
import com.example.github_searchapplication.model.Repository
import com.example.github_searchapplication.view.RepositoryWebsite

class RepositoryAdapter (var repoList: ArrayList<Repository>) : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.repo_item_layout, parent, false)

        return RepositoryViewHolder(view)
    }

    //Returns the size of the array list
    override fun getItemCount(): Int {
        return repoList.size
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {

        holder.repoName.text = repoList[position].name
        holder.forksNumber.text = repoList[position].forks.toString() + " Forks"
        holder.starsNumber.text = repoList[position].stargazersCount.toString() + " Stars"

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, RepositoryWebsite::class.java)
            intent.putExtra("repositoryUrl", repoList[position].htmlUrl)
            it.context.startActivity(intent)
        }
    }

    //Updates list with new information
    fun updateRepositoryList(updatedList : ArrayList<Repository>) {
        repoList = updatedList
        notifyDataSetChanged()
    }

    //Views initialized within the layout
    inner class RepositoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val repoName : TextView = itemView.findViewById(R.id.repository_name_textview)
        val forksNumber: TextView = itemView.findViewById(R.id.forks_textview)
        val starsNumber : TextView = itemView.findViewById(R.id.stars_textview)
    }

}