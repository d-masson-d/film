package com.example.myapplication.data

data class Film(
    val id: Int,
    val localized_name: String,
    val name: String,
    val year: Int,
    val rating: Double?,
    val image_url: String,
    val description: String,
    val genres: List<String>
)

data class FilmResponse(val films: List<Film>)