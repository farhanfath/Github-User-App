package com.project.githubuserapp.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.githubuserapp.R
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.project.githubuserapp.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backHome.setOnClickListener {
            finish()
        }

        val switchTheme : SwitchMaterial = findViewById(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener {_: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
            if (isChecked) {
                Toast.makeText(this@SettingsActivity, "Change to Dark Mode", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@SettingsActivity, "Change to Light Mode", Toast.LENGTH_SHORT).show()
            }

        }
    }
}