package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Film
import com.example.myapplication.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilmViewModel(private val apiService: ApiService) : ViewModel() {

    private val _films = MutableLiveData<List<Film>>()
    val films: LiveData<List<Film>> get() = _films

    fun fetchFilms() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getFilms()
                _films.postValue(response.films)
            } catch (e: Exception) {
                // Обработка ошибок
                e.printStackTrace()
            }
        }
    }
}
