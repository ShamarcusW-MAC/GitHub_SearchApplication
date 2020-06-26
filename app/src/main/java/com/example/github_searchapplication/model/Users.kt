package com.example.github_searchapplication.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

 class Users(
    val totalCount: Int? = null,
    val incompleteResults: Boolean? = null,
    val items: @RawValue List<Item>? = null
) {

 }

