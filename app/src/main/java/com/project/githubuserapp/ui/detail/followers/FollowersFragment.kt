package com.project.githubuserapp.ui.detail.followers


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.githubuserapp.R
import com.project.githubuserapp.adapter.FollowAdapter
import com.project.githubuserapp.databinding.UserfollowFragmentBinding
import com.project.githubuserapp.ui.detail.DetailUser
import com.project.githubuserapp.adapter.UserAdapter
import com.project.githubuserapp.data.models.User

class FollowersFragment : Fragment(R.layout.userfollow_fragment) {

    private var _binding : UserfollowFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: FollowAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailUser.EXTRA_USERNAME).toString()

        _binding = UserfollowFragmentBinding.bind(view)

        adapterHandler()

        binding.apply {
            userRv.setHasFixedSize(true)
            userRv.layoutManager = LinearLayoutManager(activity)
            userRv.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowersViewModel::class.java]
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun adapterHandler() {
        adapter = FollowAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallBack(object : FollowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(data.html_url)
                startActivity(intent)
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}