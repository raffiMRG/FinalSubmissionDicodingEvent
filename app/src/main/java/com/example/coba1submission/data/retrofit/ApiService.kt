package com.example.coba1submission.data.retrofit

import com.example.coba1submission.data.response.EventResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getActiveEvents(@Query("active") active: String): Call<EventResponse>
}