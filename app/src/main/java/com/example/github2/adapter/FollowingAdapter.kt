package com.example.github2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github2.databinding.ItemFollowerBinding
import com.example.github2.response.FollowingResponseItem

class FollowingAdapter(private val listFollowing:ArrayList<FollowingResponseItem>):
    RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {
    class ViewHolder(private var binding: ItemFollowerBinding):RecyclerView.ViewHolder(binding.root){
        fun itemBind(listFollowing: FollowingResponseItem){
            with(binding){
                tvFollowName.text=listFollowing.login
                Glide.with(itemView.context)
                    .load(listFollowing.avatarUrl)
                    .into(ivFollow)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingAdapter.ViewHolder {
        val itemFollowerBinding = ItemFollowerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemFollowerBinding)
    }

    override fun onBindViewHolder(holder: FollowingAdapter.ViewHolder, position: Int) {
        val following =listFollowing[position]
        holder.itemBind(following)
    }

    override fun getItemCount(): Int =listFollowing.size
}