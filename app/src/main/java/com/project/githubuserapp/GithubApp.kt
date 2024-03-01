package com.project.githubuserapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.asLiveData
import com.project.githubuserapp.settings.SettingPreferences
import com.project.githubuserapp.settings.dataStore

class GithubApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val pref = SettingPreferences.getInstance(this.dataStore)
        pref.getThemeSetting().asLiveData().observeForever { isDarkModeActive: Boolean ->
            val mode = if (isDarkModeActive) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }
}
