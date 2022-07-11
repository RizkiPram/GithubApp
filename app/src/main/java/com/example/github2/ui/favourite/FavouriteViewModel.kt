package com.example.github2.ui.favourite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.github2.data.entity.FavEntity
import com.example.github2.data.room.FavDao
import com.example.github2.data.room.FavDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavouriteViewModel(application: Application): AndroidViewModel(application) {

    private var favDao:FavDao?
    private var db:FavDatabase?= FavDatabase.getInstance(application)

    init {
        favDao=db?.favDao()
    }
    fun getFavourite(): LiveData<List<FavEntity>>? {
        return favDao?.getFavourite()
    }
    fun addFavourite(username: String, id: Int, avatar_url: String){
        CoroutineScope(Dispatchers.IO).launch {
            val favourite = FavEntity(
                username,
                id,
                avatar_url
            )
            favDao?.addToFav(favourite)
        }
    }
    fun deleteFromFavourite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            favDao?.deleteFavourite(id)
        }
    }
}