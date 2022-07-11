package com.example.github2.ui.follower

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github2.api.ApiConfig
import com.example.github2.response.FollowerResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {
    companion object {
        private const val TAG = "FollowerViewModel"
    }
    private val _follower = MutableLiveData<List<FollowerResponseItem>>()
    val follower:LiveData<List<FollowerResponseItem>> = _follower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    fun getFollower(user:String){
        _isLoading.value=true
        val client=ApiConfig.getApiService().getFollower(user)
        client.enqueue(object : Callback<List<FollowerResponseItem>>{
            override fun onResponse(
                call: Call<List<FollowerResponseItem>>,
                response: Response<List<FollowerResponseItem>>
            ) {
                _isLoading.value=false
                if (response.isSuccessful){
                    _follower.value=response.body()
                }else{
                    Log.e(TAG,"onFailer ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowerResponseItem>>, t: Throwable) {
                Log.e(TAG,"onFailure ${t.message}")
            }
        })
    }
}