package com.example.coba1submission.data.retrofit

import com.example.coba1submission.data.response.EventResponse
import com.example.coba1submission.data.response.EventsResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getActiveEvents(
        @Query("active") active: String
    ): Call<EventsResponse>

    @GET("events")
    fun getsearchEvents(
        @Query("active") active: String,
        @Query("q") query: String
    ): Call<EventsResponse>

    @GET("events/{id}")
    fun getEventById(
        @Path("id") id: Int,
    ): Call<EventResponse>
}