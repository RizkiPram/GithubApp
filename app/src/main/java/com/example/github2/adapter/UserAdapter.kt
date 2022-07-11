package com.example.github2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.github2.databinding.ItemUserBinding
import com.example.github2.response.ItemsItem

class UserAdapter(private val list:ArrayList<ItemsItem>):
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private var onItemClickCallback : OnItemClickCallback?=null

    inner class ViewHolder(private var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun itemBind(listUser: ItemsItem){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(listUser)
            }
            with(binding){
                tvName.text=listUser.login
                tvUsername.text=listUser.login
                Glide.with(itemView.context)
                    .load(listUser.avatarUrl)
                    .apply(RequestOptions())
                    .into(ivAvatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemUserBinding=
            ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemUserBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = list[position]
        holder.itemBind(user)
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
    override fun getItemCount()=list.size
    interface OnItemClickCallback{
        fun onItemClicked(data: ItemsItem)
    }
}