package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.Film

class FilmDetailsFragment : Fragment() {

    private lateinit var film: Film

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            film = it.getParcelable("film") ?: return
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_film_detalis, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //элементы ui
        val filmName = view.findViewById<TextView>(R.id.filmNameTop)
        val filmImage = view.findViewById<ImageView>(R.id.imageDetail)
        val filmDescription = view.findViewById<TextView>(R.id.descriptionFragment)
        val filmGenres = view.findViewById<TextView>(R.id.filmGenre)
        val filmYear = view.findViewById<TextView>(R.id.yearFilm)
        val filmRating = view.findViewById<TextView>(R.id.ratingScore)
        val localizedNameDetail = view.findViewById<TextView>(R.id.localizedNameDetail)
        val backButton = view.findViewById<ImageButton>(R.id.backButton)

        //установка данные
        filmName.text = film.name
        filmDescription.text = film.description
        localizedNameDetail.text = film.localized_name
        filmGenres.text = film.genres.joinToString(", ")
        filmYear.text = ", ${film.year.toString()} год"
        filmRating.text = film.rating?.toString() ?: "N/A"

        Glide.with(this)
            .load(film.image_url)
            .into(filmImage)
        //кнпка back из fragment
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}

