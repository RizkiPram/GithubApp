package com.example.github2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favourite")
data class FavEntity(
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatar_url: String,
):Serializable
