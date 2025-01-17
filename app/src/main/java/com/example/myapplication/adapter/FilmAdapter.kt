package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.data.Film

class FilmAdapter(private var films: List<Film>, private val onClick: (Film) -> Unit) : RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    class FilmViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.filmCardName)
        val image: ImageView = view.findViewById(R.id.filmCardImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_card, parent, false) //к film_card
        return FilmViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = films[position]
        holder.title.text = film.localized_name


        Glide.with(holder.itemView.context)
            .load(film.image_url)
            .apply(RequestOptions().transform(RoundedCorners(4)))
            .into(holder.image)

        //обр клика
        holder.itemView.setOnClickListener {
            onClick(film)
        }
    }

    override fun getItemCount() = films.size



}

