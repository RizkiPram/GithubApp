package com.example.github2.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: List<ItemsItem>
) : Parcelable

@Parcelize
data class ItemsItem(

    @field:SerializedName("login")
    @ColumnInfo(name="login")
    val login: String,

    @field:SerializedName("avatar_url")
    @ColumnInfo(name="avatarUrl")
    val avatarUrl: String,

    @field:SerializedName("id")
    val id: Int,

) : Parcelable