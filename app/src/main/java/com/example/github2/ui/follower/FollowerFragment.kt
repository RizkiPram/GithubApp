package com.example.github2.ui.follower

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github2.ui.detail.DetailActivity.Companion.username
import com.example.github2.adapter.FollowerAdapter
import com.example.github2.databinding.FragmentFollowerBinding
import com.example.github2.response.FollowerResponseItem

class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowerBinding?=null
    private val binding get() =_binding!!
    private val followerViewModel : FollowerViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followerViewModel.follower.observe(viewLifecycleOwner){
            setFollowerData(it)
        }
        followerViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
        followerViewModel.getFollower(username)}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun setFollowerData(followerData:List<FollowerResponseItem>){
        val listFollower =ArrayList<FollowerResponseItem>()
        for (follower in followerData){
            listFollower.add(follower)
        }
        val adapter = FollowerAdapter(listFollower)
        binding.rvFollower.layoutManager= LinearLayoutManager(context)
        binding.rvFollower.adapter=adapter
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}