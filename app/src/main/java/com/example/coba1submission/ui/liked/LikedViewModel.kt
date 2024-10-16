package com.example.coba1submission.ui.liked

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coba1submission.data.response.Event
import com.example.coba1submission.data.database.Event as dbEvent
import com.example.coba1submission.data.response.EventResponse
import com.example.coba1submission.data.response.EventsResponse
import com.example.coba1submission.data.response.ListEventsItem
import com.example.coba1submission.data.retrofit.ApiConfig
import com.example.coba1submission.databinding.ItemRowBinding
import com.example.coba1submission.ui.details.DetailsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LikedViewModel(var id: Int): ViewModel() {
    private val _eventsResponse = MutableLiveData<EventResponse>()
    val eventsResponse: LiveData<EventResponse> = _eventsResponse

    private val _listEvents = MutableLiveData<Event>()
    val listEvents: LiveData<Event> = _listEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "LikedViewModel"
    }

    fun findRestaurant() {
        _isLoading.value = true
        val client = ApiConfig.getApiSearchById().getEventById(id)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        _eventsResponse.value = it
                        _listEvents.value = it.event
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


//class LikedViewModel(private val events: List<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
//
//    // Pastikan EventViewHolder ada di dalam EventAdapter
//    class EventViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(event: Event) {
//            binding.eventTitle.text = event.name
//            Glide.with(binding.root.context)
//                .load(event.imageLogo)
//                .into(binding.recImage)
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
//        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return EventViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
//        val event = events[position]
//        holder.bind(event)
//    }
//
//    override fun getItemCount() = events.size
//}