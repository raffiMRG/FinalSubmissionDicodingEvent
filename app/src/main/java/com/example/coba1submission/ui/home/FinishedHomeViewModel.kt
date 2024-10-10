package com.example.coba1submission.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coba1submission.data.response.EventResponse
import com.example.coba1submission.data.response.ListEventsItem
import com.example.coba1submission.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedHomeViewModel : ViewModel() {

    private val _eventResponse = MutableLiveData<EventResponse>()
    val eventResponse: LiveData<EventResponse> = _eventResponse

    private val _listEvents = MutableLiveData<List<ListEventsItem>>()
    val listEvents: LiveData<List<ListEventsItem>> = _listEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FinishedHomeViewModel"
        private const val ACTIVE = "0"
    }

    fun findEvent() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getActiveEvents(ACTIVE)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        _eventResponse.value = it
                        val limitedList = if (it.listEvents.size > 5) it.listEvents.subList(0, 5) else it.listEvents
                        _listEvents.value = limitedList
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}