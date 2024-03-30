package com.project.githubuserapp.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.githubuserapp.R
import com.project.githubuserapp.adapter.UserAdapter
import com.project.githubuserapp.data.models.User
import com.project.githubuserapp.databinding.ActivityMainBinding
import com.project.githubuserapp.ui.detail.DetailUserActivity
import com.project.githubuserapp.ui.favorite.FavoriteUserActivity
import com.project.githubuserapp.ui.settings.SettingsActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()

        adapterHandler()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        searchViewHandler()
        rvHandler()
        getListUserHandler()
    }

    private fun getListUserHandler() {
        viewModel.getSearchUsers().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    private fun rvHandler() {
        binding.apply {
            userRv.layoutManager = LinearLayoutManager(this@MainActivity)
            userRv.setHasFixedSize(true)
            userRv.adapter = adapter
        }
    }

    private fun searchViewHandler() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchBarMenuHandler()
            searchView.editText.setOnEditorActionListener { textView, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val query = textView.text.toString().trim()
                    if (query.isNotEmpty()) {
                        searchUser(query)
                    }
                    searchView.hide()
                    return@setOnEditorActionListener  true
                }
                return@setOnEditorActionListener false
            }
        }
    }

    private fun searchBarMenuHandler() {
        with(binding) {
            searchBar.inflateMenu(R.menu.custom_menu)
            searchBar.setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.settings -> {
                        try {
                            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                            startActivity(intent)
                            return@setOnMenuItemClickListener true
                        } catch (e: Exception) {
                            Log.e("IntentError", "Error creating intent: ${e.message}")
                            return@setOnMenuItemClickListener false
                        }
                    }
                    R.id.favorite -> {
                        try {
                            val intent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                            startActivity(intent)
                            return@setOnMenuItemClickListener true
                        } catch (e: Exception) {
                            Log.e("IntentError", "Error creating intent: ${e.message}")
                            return@setOnMenuItemClickListener false
                        }
                    }
                    else -> return@setOnMenuItemClickListener false
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun adapterHandler() {
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallBack(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatar_url)
                    it.putExtra(DetailUserActivity.EXTRA_URL, data.html_url)
                    startActivity(it)
                }
            }
        })
    }

    private fun showLoading(state: Boolean) {
        binding.apply {
            if (state) {
                progressBar.visibility = View.VISIBLE
                userRv.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                userRv.visibility = View.VISIBLE
            }
        }
    }

    private fun searchUser(query: String) {
        showLoading(true)
        viewModel.setSearchUsers(query)
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            viewModel.getSearchUsers().observe(this) { users ->
                if (users.isNullOrEmpty()) {
                    Toast.makeText(this, "Data Not Found", Toast.LENGTH_SHORT).show()
                } else {
                    adapter.setList(users)
                }
            }
        } else {
            lifecycleScope.launch {
                delay(1000)
                showLoading(false)
            }
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }
}