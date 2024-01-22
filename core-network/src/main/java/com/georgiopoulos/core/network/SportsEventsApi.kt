package com.georgiopoulos.core.network

import com.georgiopoulos.core.network.response.SportEventsResponse
import retrofit2.http.GET

interface SportsEventsApi {

    @GET(".")
    suspend fun getSportEventList(): List<SportEventsResponse>
}

