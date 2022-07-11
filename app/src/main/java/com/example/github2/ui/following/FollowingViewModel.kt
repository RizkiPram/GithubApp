package com.example.github2.ui.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github2.api.ApiConfig
import com.example.github2.response.FollowingResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    companion object{
        private const val TAG = "FollowingViewModel"
    }

    private val _following = MutableLiveData<List<FollowingResponseItem>>()
    val following:LiveData<List<FollowingResponseItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowing(user:String){
        _isLoading.value=true
        val client = ApiConfig.getApiService().getFollowing(user)
        client.enqueue(object : Callback<List<FollowingResponseItem>>{
            override fun onResponse(
                call: Call<List<FollowingResponseItem>>,
                response: Response<List<FollowingResponseItem>>
            ) {
                _isLoading.value=false
                if (response.isSuccessful){
                    _following.value=response.body()
                }else{
                    Log.e(TAG,"onFailure ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowingResponseItem>>, t: Throwable) {
                _isLoading.value=false
                Log.e(TAG,"onFailure ${t.message}")
            }
        })
    }
}