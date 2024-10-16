package com.example.coba1submission.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(event: Event)

    @Update
    fun update(event: Event)

    @Delete
    fun delete(event: Event)

    @Query("SELECT * FROM event ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Event>>

    @Query("SELECT COUNT(*) > 0 FROM event WHERE id = :id")
    fun isEventExists(id: Int): Boolean
}