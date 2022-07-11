package com.example.github2.ui.favourite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github2.adapter.FavouriteAdapter
import com.example.github2.data.entity.FavEntity
import com.example.github2.databinding.ActivityFavouriteBinding
import com.example.github2.response.ItemsItem
import com.example.github2.ui.detail.DetailActivity

class FavouriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteBinding
    private val favouriteViewModel:FavouriteViewModel by viewModels()
    private lateinit var adapter: FavouriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title="List Favourite"
        adapter = FavouriteAdapter()
        with(binding){
            val layoutManager = LinearLayoutManager(this@FavouriteActivity)
            rvFavourite.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(this@FavouriteActivity, layoutManager.orientation)
            rvFavourite.addItemDecoration(itemDecoration)
            rvFavourite.adapter=adapter
            adapter.setOnItemClickCallback (object : FavouriteAdapter.OnItemClickCallback{
                override fun onItemClicked(data: ItemsItem) {
                    showDetail(data)
                }
            })
            favouriteViewModel.getFavourite()?.observe(this@FavouriteActivity){
                if (it != null){
                    val list=favList(it)
                    adapter.setList(list)
                }
            }
        }
    }
    private fun favList(it: List<FavEntity>): ArrayList<ItemsItem> {
        val listUsers = ArrayList<ItemsItem>()
        for(user in it){
            val userFavourite = ItemsItem(
                user.login,
                user.avatar_url,
                user.id
            )
            listUsers.add(userFavourite)
        }
        return listUsers
    }
    private fun showDetail(data :ItemsItem){
        val intent= Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DETAIL,data)
        startActivity(intent)
    }
}