package com.example.myapplication.network

import com.example.myapplication.data.FilmResponse
import retrofit2.http.GET

interface ApiService {
    @GET("films.json")
    suspend fun getFilms(): FilmResponse
}
