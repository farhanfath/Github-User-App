package com.project.githubuserapp.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.githubuserapp.R
import com.project.githubuserapp.adapter.FavoriteAdapter
import com.project.githubuserapp.data.db.UserFavorite
import com.project.githubuserapp.data.models.User
import com.project.githubuserapp.databinding.ActivityFavoriteUserBinding
import com.project.githubuserapp.ui.detail.DetailUserActivity
import com.project.githubuserapp.ui.detail.DetailUserViewModel

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: FavoriteAdapter
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var removeFavViewModel : DetailUserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBarHandler()

        viewModelHandler()

        adapterHandler()

        rvFavHandler()

        getListUserHandler()
    }

    private fun getListUserHandler() {
        viewModel.getUserFavorite()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        }
    }

    private fun rvFavHandler() {
        binding.apply {
            rvUserFav.setHasFixedSize(true)
            rvUserFav.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
            rvUserFav.adapter = adapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun adapterHandler() {
        adapter = FavoriteAdapter(removeFavViewModel)
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallBack(object : FavoriteAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteUserActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })
    }

    private fun viewModelHandler() {
        removeFavViewModel = ViewModelProvider(this)[DetailUserViewModel::class.java]
        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
    }

    private fun supportActionBarHandler() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.favoriteTitle)
    }

    private fun mapList(users: List<UserFavorite>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                user.login,
                user.id,
                user.avatar_url,
                user.html_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
        }
        return true
    }
}