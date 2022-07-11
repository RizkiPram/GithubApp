package com.example.github2.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.github2.data.entity.FavEntity

@Database(entities = [FavEntity::class], version = 1)
abstract class FavDatabase:RoomDatabase() {
abstract fun favDao():FavDao
        companion object{
            private var instance:FavDatabase?=null

            fun getInstance(context: Context):FavDatabase?{
                if (instance == null){
                    synchronized(FavDatabase::class){
                    instance = Room.databaseBuilder(context.applicationContext,FavDatabase::class.java,"fav.db")
                        .build()
                    }
                }
                return instance
            }
        }

}