package com.hera.reminderapp.data.network

import com.hera.reminderapp.data.Reminder
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("v3/b637a5c9-2181-46e9-9a07-d770dcaf17d4")
    suspend fun getReminders(): Response<Reminder>
}

