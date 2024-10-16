package com.example.coba1submission.ui.liked

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.coba1submission.data.database.Event
import com.example.coba1submission.ui.details.DetailsViewModel

class EventViewModel : ViewModel() {
    private lateinit var detailsViewModel: DetailsViewModel

    val allEvents: LiveData<List<Event>> = detailsViewModel.getAllData()
}