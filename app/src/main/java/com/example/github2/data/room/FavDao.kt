package com.example.github2.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.github2.data.entity.FavEntity

@Dao
interface FavDao {

    @Insert
    suspend fun addToFav(favEntity: FavEntity)

    @Query("SELECT * FROM favourite")
    fun getFavourite():LiveData<List<FavEntity>>

    @Query("SELECT count(*) FROM favourite WHERE favourite.id= :id")
    suspend fun checkFavourite(id:Int):Int

    @Query("DELETE FROM favourite WHERE favourite.id = :id")
    suspend fun deleteFavourite(id: Int):Int
}