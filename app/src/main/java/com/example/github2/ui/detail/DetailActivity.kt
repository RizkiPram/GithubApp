package com.example.github2.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.github2.R
import com.example.github2.SectionsPagerAdapter
import com.example.github2.databinding.ActivityDetailBinding
import com.example.github2.response.ItemsItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<ItemsItem>(EXTRA_DETAIL) as ItemsItem

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }
        detailViewModel.detail.observe(this){
            with(binding){
                binding.tvDetailName.text=it.name
                binding.tvDetailUsername.text=it.login
                binding.tvDetailFollower.text="${it.followers} Follower"
                binding.tvDetailLocation.text=it.location
                binding.tvDetailCompany.text=it.company
                Glide.with(this@DetailActivity)
                    .load(it.avatarUrl)
                    .into(detailAvatar)
                supportActionBar?.title=it.login
            }
        }
        detailViewModel.setDetail(user.login)
        username =user.login
        id=user.id
        avatarUrl=user.avatarUrl
        with(binding){
            var isChecked = false
            CoroutineScope(Dispatchers.IO).launch {
                val count = detailViewModel.checkFavourite(id)
                withContext(Dispatchers.Main){
                    if (count!=null){
                        if(count>0){
                            favButton.isChecked=true
                            isChecked = true
                        }else{
                            favButton.isChecked=false
                            isChecked = false
                        }
                    }
                }

            }
            favButton.setOnClickListener {
                isChecked =! isChecked
                if (isChecked){
                    detailViewModel.addFavourite(username,id, avatarUrl)
                }else{
                    detailViewModel.deleteFromFavourite(id)
                }
                favButton.isChecked= isChecked
            }
        }
        val sectionsPagerAdapter= SectionsPagerAdapter(this)
        val viewPager:ViewPager2 =binding.viewPager
        viewPager.adapter=sectionsPagerAdapter
        val tabs:TabLayout=binding.tabs
        TabLayoutMediator(tabs,viewPager){tab,position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        lateinit var username: String
        var id:Int = 0
        lateinit var avatarUrl:String
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}