package com.example.github_searchapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.example.github_searchapplication.R

class Repository_Website : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository__website)

        val intent = intent

        val repoWeb = findViewById<WebView>(R.id.repository_webview)

        repoWeb.loadUrl(intent.getStringExtra("repositoryUrl"))

    }
}
