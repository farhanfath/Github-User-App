package com.project.githubuserapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.githubuserapp.R
import com.project.githubuserapp.data.models.User
import com.project.githubuserapp.databinding.ItemUserFavBinding
import com.project.githubuserapp.ui.detail.DetailUserViewModel

class FavoriteAdapter(private val viewModel: DetailUserViewModel) : RecyclerView.Adapter<FavoriteAdapter.UserViewHolder>() {

    private val list = ArrayList<User>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(users: ArrayList<User>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemUserFavBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.apply {

                binding.root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(user)
                }

                usernameTv.text = user.login
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .into(userIv)

                removeFavBtn.setOnClickListener {
                    viewModel.removeUserFromFavorite(user.id)
                    showRemoveFavToast(user.login)
                }
            }
        }

        private fun showRemoveFavToast(username: String) {
            val context = binding.root.context
            val message = context.getString(R.string.removedUserFav, username)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUserFavBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}