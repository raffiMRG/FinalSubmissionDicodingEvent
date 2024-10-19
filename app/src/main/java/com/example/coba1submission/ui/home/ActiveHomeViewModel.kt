package com.example.coba1submission.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coba1submission.data.response.EventsResponse
import com.example.coba1submission.data.response.ListEventsItem
import com.example.coba1submission.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActiveHomeViewModel : ViewModel() {

    private val _eventsResponse = MutableLiveData<EventsResponse>()

    private val _listEvents = MutableLiveData<List<ListEventsItem>>()
    val listEvents: LiveData<List<ListEventsItem>> = _listEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFailure = MutableLiveData<Boolean>()
    val isFailure: LiveData<Boolean> = _isFailure

    private val _responseMessage = MutableLiveData<String>()
    val responseMessage: LiveData<String> = _responseMessage


    companion object {
        private const val TAG = "ActiveHomeViewModel"
        private const val ACTIVE = "1"
    }

    fun findEvent() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getActiveEvents(ACTIVE)
        client.enqueue(object : Callback<EventsResponse> {
            override fun onResponse(
                call: Call<EventsResponse>,
                response: Response<EventsResponse>
            ) {
                _isLoading.value = false
                _isFailure.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        _eventsResponse.value = it
                        val limitedList = if (it.listEvents.size > 5) it.listEvents.subList(0, 5) else it.listEvents
                        _listEvents.value = limitedList
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                _isLoading.value = false
                _isFailure.value = true
                _responseMessage.value = "Gagal Memuat Halaman, Silahkan Periksa koneksi anda\n\n Failure: ${t.message}"
//                Log.d("messageLangsung", "onFailure: ${t.message}")
            }
        })
    }
}