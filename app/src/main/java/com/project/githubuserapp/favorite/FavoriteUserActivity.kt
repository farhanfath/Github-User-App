package com.project.githubuserapp.favorite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.githubuserapp.databinding.ActivityFavoriteUserBinding
import com.project.githubuserapp.db.UserFavorite
import com.project.githubuserapp.detail.DetailUser
import com.project.githubuserapp.main.UserAdapter
import com.project.githubuserapp.models.User

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        adapter.setOnItemClickCallBack(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteUserActivity, DetailUser::class.java).also {
                    it.putExtra(DetailUser.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUser.EXTRA_ID, data.id)
                    it.putExtra(DetailUser.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }

        })

        binding.apply {
            rvUserFav.setHasFixedSize(true)
            rvUserFav.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
            rvUserFav.adapter = adapter
            backHome.setOnClickListener {
                finish()
            }
        }

        viewModel.getUserFavorite()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        }
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
}