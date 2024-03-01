package com.project.githubuserapp.detail.following


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.githubuserapp.R
import com.project.githubuserapp.databinding.UserfollowFragmentBinding
import com.project.githubuserapp.detail.DetailUser
import com.project.githubuserapp.main.UserAdapter

class FollowingFragment : Fragment(R.layout.userfollow_fragment) {

    private var _binding : UserfollowFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailUser.EXTRA_USERNAME).toString()

        _binding = UserfollowFragmentBinding.bind(view)



        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            userRv.setHasFixedSize(true)
            userRv.layoutManager = LinearLayoutManager(activity)
            userRv.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowingViewModel::class.java]
        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}