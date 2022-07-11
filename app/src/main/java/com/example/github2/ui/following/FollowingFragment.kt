package com.example.github2.ui.following

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github2.ui.detail.DetailActivity.Companion.username
import com.example.github2.adapter.FollowingAdapter
import com.example.github2.databinding.FragmentFollowingBinding
import com.example.github2.response.FollowingResponseItem

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding?=null
    private val binding get() =_binding!!
    private val followingViewModel : FollowingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followingViewModel.following.observe(viewLifecycleOwner){
            setFollowingData(it)
        }
        followingViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
        followingViewModel.getFollowing(username)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(layoutInflater)
        return binding.root
    }
    private fun setFollowingData(followingData:List<FollowingResponseItem>){
    val listFollowing =ArrayList<FollowingResponseItem>()
        for (following in followingData){
            listFollowing.add(following)
        }
        val adapter = FollowingAdapter(listFollowing)
        binding.rvFollowing.layoutManager=LinearLayoutManager(context)
        binding.rvFollowing.adapter=adapter
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}