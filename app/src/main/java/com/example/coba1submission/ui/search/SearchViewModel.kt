package com.example.coba1submission.ui.search

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

class SearchViewModel : ViewModel() {

    private val _restaurant = MutableLiveData<EventResponse>()
    val restaurant: LiveData<EventResponse> = _restaurant

    private val _listEvents = MutableLiveData<List<ListEventsItem>>()
    val listEvents: LiveData<List<ListEventsItem>> = _listEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _query = MutableLiveData<String>()
    var query: LiveData<String> = _query

    companion object {
        private const val TAG = "SearchViewModel"
        private const val ACTIVE = "-1"
    }

    init{
        _query.observeForever { keyword -> findEvent(keyword)}
    }

    fun setQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun findEvent(keyword: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiSearch().getsearchEvents(ACTIVE, keyword)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        _restaurant.value = it
                        _listEvents.value = it.listEvents
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