package com.project.githubuserapp.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.githubuserapp.R
import com.project.githubuserapp.settings.SettingsActivity
import com.project.githubuserapp.databinding.ActivityMainBinding
import com.project.githubuserapp.detail.DetailUser
import com.project.githubuserapp.favorite.FavoriteUserActivity
import com.project.githubuserapp.models.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallBack(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUser::class.java).also {
                    it.putExtra(DetailUser.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUser.EXTRA_ID, data.id)
                    it.putExtra(DetailUser.EXTRA_AVATAR, data.avatar_url)
                    it.putExtra(DetailUser.EXTRA_URL, data.html_url)
                    startActivity(it)
                }
            }

        })

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        binding.apply {
            userRv.layoutManager = LinearLayoutManager(this@MainActivity)
            userRv.setHasFixedSize(true)
            userRv.adapter = adapter

            userSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrEmpty()) {
                        searchUser(query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                   return false
                }

            })
        }

        viewModel.getSearchUsers().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.apply {
            if (state) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun searchUser(query: String) {
        showLoading(true)
        viewModel.setSearchUsers(query)
        viewModel.getSearchUsers().observe(this) { users ->
            if (users.isNullOrEmpty()) {
                Toast.makeText(this, "Data Not Found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setList(users)
            }
            lifecycleScope.launch {
                delay(1000)
                showLoading(false)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.custom_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.favorite -> {
                val intent = Intent(this, FavoriteUserActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}