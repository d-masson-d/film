package com.example.myapplication.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.Genre

class GenreAdapter(
    private val genres: List<Genre>,
    private val onGenreClick: (Genre) -> Unit
) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    private var selectedPosition: Int = -1

    inner class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val genreName: TextView = itemView.findViewById(R.id.genreName)

        fun bind(genre: Genre, isSelected: Boolean) {
            genreName.text = genre.name
            itemView.setBackgroundColor(if (isSelected) Color.parseColor("#FFC967") else Color.WHITE) //покраска жанров
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.genre_card, parent, false)
        return GenreViewHolder(view)
    }
//для жанров выделение
    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) { //работает
        val genre = genres[position]
        holder.bind(genre, position == selectedPosition)
        holder.itemView.setOnClickListener {
            if (selectedPosition == position) {
                //снять выделение
                selectedPosition = -1
                onGenreClick(Genre("")) //пустой жанр для сброса
            } else {
                selectedPosition = position
                onGenreClick(genre)
            }
            notifyDataSetChanged() //обнов адаптер
        }
    }

    override fun getItemCount(): Int {
        return genres.size
    }
}

