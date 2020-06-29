package com.example.github_searchapplication.model


import kotlinx.android.parcel.RawValue

 class Users(
    val totalCount: Int? = null,
    val incompleteResults: Boolean? = null,
    val items: @RawValue List<Item>? = null
) {

 }

