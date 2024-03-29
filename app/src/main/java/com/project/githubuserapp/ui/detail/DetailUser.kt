package com.project.githubuserapp.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.project.githubuserapp.R
import com.project.githubuserapp.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUser : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }

    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatar = intent.getStringExtra(EXTRA_AVATAR)
        val url = intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this)[DetailUserViewModel::class.java]
        binding.progressBar.visibility = View.VISIBLE

        if (username != null) {
            viewModel.setUserDetail(username)
            detailUserDataHandler()
        }

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    isChecked = count > 0
                    binding.fabFav.setImageResource(if (isChecked) R.drawable.fav_fill else R.drawable.fav_unfill)
                }
            }
        }

        binding.fabFav.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                if (username != null && avatar != null && url != null) {
                    viewModel.addToFavorite(username, id, avatar, url)
                    Toast.makeText(this, "Berhasil Ditambahkan Ke Favorit", Toast.LENGTH_SHORT).show()
                }
            } else {
                viewModel.removeUserFromFavorite(id)
                Toast.makeText(this, "Berhasil Dihapus Dari Favorit", Toast.LENGTH_SHORT).show()
            }
            binding.fabFav.setImageResource(if (isChecked) R.drawable.fav_fill else R.drawable.fav_unfill)
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }

        binding.apply {
            backBtn.setOnClickListener {
                finish()
            }
            addBtn.setOnClickListener {
                onAddButtonClicked()
            }
            shareBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                if (url != null){
                    intent.putExtra(Intent.EXTRA_TEXT, ("Follow On Github : $url"))
                } else {
                    intent.putExtra(Intent.EXTRA_TEXT, ("Follow On Github : $username"))
                }
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Send To"))
            }
        }
    }

    private fun detailUserDataHandler() {
        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    nameTv.text = it.name
                    usernameTv.text = it.login
                    followersTv.text = resources.getString(R.string.follower_count, it.followers)
                    followingTv.text = resources.getString(R.string.following_count, it.following)

                    Glide.with(this@DetailUser)
                        .load(it.avatar_url)
                        .into(userCiv)

                    bioTv.text = it.bio

                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        binding.apply {
        if(!clicked){
            backBtn.visibility = View.VISIBLE
            shareBtn.visibility = View.VISIBLE
            fabFav.visibility = View.VISIBLE
        }else{
            backBtn.visibility = View.INVISIBLE
            shareBtn.visibility = View.INVISIBLE
            fabFav.visibility = View.INVISIBLE
        }
        }
    }

    private fun setAnimation(clicked: Boolean) {

        binding.apply {
        if(!clicked){
            backBtn.startAnimation(fromBottom)
            shareBtn.startAnimation(fromBottom)
            fabFav.startAnimation(fromBottom)
            addBtn.startAnimation(rotateOpen)
        }else{
            backBtn.startAnimation(toBottom)
            shareBtn.startAnimation(toBottom)
            fabFav.startAnimation(toBottom)
            addBtn.startAnimation(rotateClose)
        }
        }
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
        const val EXTRA_URL = "extra_url"
    }
}