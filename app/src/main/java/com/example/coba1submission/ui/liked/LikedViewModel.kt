package com.example.coba1submission.ui.liked

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coba1submission.data.response.Event
import com.example.coba1submission.data.response.EventResponse
import com.example.coba1submission.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class LikedViewModel(private val idsLiveData: MutableLiveData<MutableList<Int>>) : ViewModel() {

    private val _eventsResponseList = MutableLiveData<List<Event>>()
    val eventsResponseList: LiveData<List<Event>> = _eventsResponseList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        idsLiveData.observeForever { newIds ->
            fetchEvents(newIds)
        }
    }

    fun updateIds(newIds: List<Int>) {
        idsLiveData.value = newIds.toMutableList()
    }

    private fun fetchEvents(newIds: List<Int>) {
        _isLoading.value = true
        val events = mutableListOf<Event>()
        var remainingCalls = newIds.size

        newIds.forEach { id ->
            val client = ApiConfig.getApiService().getEventById(id)
            client.enqueue(object : Callback<EventResponse> {
                override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.event?.let { event ->
                            events.add(event)
                        }
                    }
                    remainingCalls--
                    checkIfAllCallsComplete(events, remainingCalls)
                }

                override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                    remainingCalls--
                    checkIfAllCallsComplete(events, remainingCalls)
                }
            })
        }
    }

    private fun checkIfAllCallsComplete(events: List<Event>, remainingCalls: Int) {
        if (remainingCalls == 0) {
            _isLoading.value = false
            _eventsResponseList.value = events
        }
    }

    companion object {
        private const val TAG = "LikedViewModel"
    }
}