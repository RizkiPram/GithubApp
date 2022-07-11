 package com.example.github2.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github2.R
import com.example.github2.adapter.UserAdapter
import com.example.github2.databinding.ActivityMainBinding
import com.example.github2.response.ItemsItem
import com.example.github2.ui.detail.DetailActivity
import com.example.github2.ui.favourite.FavouriteActivity
import com.example.github2.ui.setting.SettingActivity
import com.example.github2.ui.setting.SettingViewModel
import com.example.github2.utils.SettingPreferences
import com.example.github2.utils.ViewModelFactory
 private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
 class MainActivity  : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
        binding.moveFavButton.setOnClickListener {
            val intent = Intent(this,FavouriteActivity::class.java)
            startActivity(intent)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }
        mainViewModel.follower.observe(this){
            setSearchData(it)
        }
        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val search =binding.searchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        search.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.setSearchUser(query)
                search.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })

    }

     override fun onCreateOptionsMenu(menu: Menu): Boolean {
         val inflater = menuInflater
         inflater.inflate(R.menu.option_menu, menu)
         return super.onCreateOptionsMenu(menu)
     }

     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         when(item.itemId){
             R.id.setting->{
                 val intent = Intent(this, SettingActivity::class.java)
                 startActivity(intent )
             }
         }
         return super.onOptionsItemSelected(item)
     }
     private fun setSearchData(data:List<ItemsItem>){
            val listUser = ArrayList<ItemsItem>()
            for (id in data){
                listUser.add(id)
            }
            val adapter= UserAdapter(listUser)
            binding.rvUser.adapter=adapter
         adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
             override fun onItemClicked(data: ItemsItem) {
                 showDetail(data)
             }})
     }
     private fun showDetail(data :ItemsItem){
        val intent= Intent(this, DetailActivity::class.java)
         intent.putExtra(DetailActivity.EXTRA_DETAIL,data)
         startActivity(intent)
     }
     private fun showLoading(isLoading: Boolean) {
         binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
     }
}