package com.example.github2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github2.databinding.ItemFollowerBinding
import com.example.github2.response.FollowerResponseItem

class FollowerAdapter(private val listFollower: ArrayList<FollowerResponseItem>):
    RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {
    class ViewHolder(private var binding: ItemFollowerBinding) : RecyclerView.ViewHolder(binding.root){
        fun itemBind(listFollower : FollowerResponseItem){
            with(binding){
                tvFollowName.text=listFollower.login
                Glide.with(itemView.context)
                    .load(listFollower.avatarUrl)
                    .into(ivFollow)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemFollowerBinding = ItemFollowerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemFollowerBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val follower =listFollower[position]
        holder.itemBind(follower)
    }

    override fun getItemCount(): Int = listFollower.size
}