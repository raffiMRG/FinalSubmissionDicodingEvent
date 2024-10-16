package com.example.coba1submission.ui.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.coba1submission.data.database.Event
import com.example.coba1submission.data.repository.EventRepository

class DetailsViewModel(application: Application) : ViewModel() {

    private val mNoteRepository: EventRepository = EventRepository(application)

    fun insert(event: Event) {
        mNoteRepository.insert(event)
    }

    fun update(event: Event) {
        mNoteRepository.update(event)
    }

    fun delete(event: Event) {
        mNoteRepository.delete(event)
    }

    fun getAllData(): LiveData<List<Event>>{
        return mNoteRepository.getAllNotes()
    }

    fun getItemById(id: Int): Boolean {
        return mNoteRepository.isEventExists(id)
    }

}