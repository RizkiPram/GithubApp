package com.example.github2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github2.databinding.ItemFavouriteBinding
import com.example.github2.response.ItemsItem

class FavouriteAdapter:
    RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {
    private var onItemClickCallback : OnItemClickCallback?=null
    private val list = ArrayList<ItemsItem>()


    fun setList(item: ArrayList<ItemsItem>){
        list.clear()
        list.addAll(item)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private var binding: ItemFavouriteBinding): RecyclerView.ViewHolder(binding.root) {
        fun itemBind(listFav:ItemsItem){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(listFav)
            }
            with(binding){
                tvFavouriteName.text=listFav.login
                tvFavouriteUsername.text=listFav.login
                Glide.with(itemView.context)
                    .load(listFav.avatarUrl)
                    .into(ivFavouriteAvatar)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteAdapter.ViewHolder {
        val itemFavouriteBinding = ItemFavouriteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemFavouriteBinding)
    }

    override fun onBindViewHolder(holder: FavouriteAdapter.ViewHolder, position: Int) {
        val favourite = list[position]
        holder.itemBind(favourite)
    }
    override fun getItemCount(): Int =list.size
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
    interface OnItemClickCallback{
        fun onItemClicked(data: ItemsItem)
    }
}