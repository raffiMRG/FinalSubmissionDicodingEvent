package com.example.coba1submission.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.coba1submission.data.database.Event
import com.example.coba1submission.data.database.EventDao
import com.example.coba1submission.data.database.EventRoomDatabase
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class EventRepository(application: Application) {
    private val mNotesDao: EventDao
        private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = EventRoomDatabase.getDatabase(application)
        mNotesDao = db.eventDao()
    }

    fun getAllNotes(): LiveData<List<Event>> = mNotesDao.getAllNotes()
    fun insert(event: Event) {
        executorService.execute { mNotesDao.insert(event) }
    }

    fun delete(event: Event) {
        executorService.execute { mNotesDao.delete(event) }
    }

    fun update(event: Event) {
        executorService.execute { mNotesDao.update(event) }
    }

    fun isEventExists(id: Int): Boolean {
        return executorService.submit(Callable {
            mNotesDao.isEventExists(id)
        }).get() // Menggunakan get() untuk mendapatkan hasil Boolean dari Future
    }
}
