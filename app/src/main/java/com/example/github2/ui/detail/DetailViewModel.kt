package com.example.github2.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github2.api.ApiConfig
import com.example.github2.data.entity.FavEntity
import com.example.github2.data.room.FavDao
import com.example.github2.data.room.FavDatabase
import com.example.github2.response.DetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): AndroidViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    private val _detail = MutableLiveData<DetailResponse>()
    val detail :LiveData<DetailResponse> = _detail

    private var favDao:FavDao?
    private var db:FavDatabase?= FavDatabase.getInstance(application)
    init {
        favDao=db?.favDao()
    }
    fun setDetail(user : String){
        _isLoading.value=true
        val client=ApiConfig.getApiService().getDetail(user)
        client.enqueue(object: Callback<DetailResponse>{
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value=false
                val responseBody = response.body()
                if (response.isSuccessful){
                    if (responseBody != null) {
                        _detail.value=responseBody!!
                    }
                }else{
                    Log.e(TAG,"onFailure:${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value=false
                Log.e(TAG,"onFailure:${t.message}")
            }
        })
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
    suspend fun checkFavourite(id: Int)=favDao?.checkFavourite(id)

    fun deleteFromFavourite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            favDao?.deleteFavourite(id)
        }
    }
    companion object {
        private const val TAG = "DetailViewModel"
    }
}