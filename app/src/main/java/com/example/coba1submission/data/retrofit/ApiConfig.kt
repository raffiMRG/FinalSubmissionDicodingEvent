package com.example.coba1submission.data.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://event-api.dicoding.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }

        fun getApiSearch(): ApiSearch {
            // Membuat interceptor untuk logging request dan response
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // Menampilkan body request dan response
            }

            // Membangun OkHttpClient dengan interceptor logging
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            // Membangun Retrofit dengan base URL dan converter factory
            val retrofit = Retrofit.Builder()
                .baseUrl("https://event-api.dicoding.dev/") // Base URL API
                .addConverterFactory(GsonConverterFactory.create()) // Menggunakan GSON untuk konversi JSON
                .client(client) // Menggunakan OkHttpClient yang telah dibuat
                .build()

            // Mengembalikan instance ApiService
            return retrofit.create(ApiSearch::class.java)
        }

        fun getApiSearchById(): ApiSearchById {
            // Membuat interceptor untuk logging request dan response
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) // Menampilkan body request dan response

            // Membangun OkHttpClient dengan interceptor logging
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            // Membangun Retrofit dengan base URL dan converter factory
            val retrofit = Retrofit.Builder()
                .baseUrl("https://event-api.dicoding.dev/") // Base URL API
                .addConverterFactory(GsonConverterFactory.create()) // Menggunakan GSON untuk konversi JSON
                .client(client) // Menggunakan OkHttpClient yang telah dibuat
                .build()

            // Mengembalikan instance ApiService
            return retrofit.create(ApiSearchById::class.java)
        }
    }
}