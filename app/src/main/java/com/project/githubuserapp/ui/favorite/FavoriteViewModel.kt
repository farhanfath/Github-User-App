package com.project.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.project.githubuserapp.data.db.UserDatabase
import com.project.githubuserapp.data.db.UserFavorite
import com.project.githubuserapp.data.db.UserFavoriteDao

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: UserFavoriteDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.userFavoriteDao()
    }

    fun getUserFavorite(): LiveData<List<UserFavorite>>? {
        return userDao?.getFavoriteUser()
    }
}