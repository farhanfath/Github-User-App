package com.project.githubuserapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user_favorite")
data class UserFavorite(
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatar_url: String,
    val html_url: String
): Serializable
