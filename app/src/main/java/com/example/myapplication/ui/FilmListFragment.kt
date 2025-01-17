package com.example.myapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.FilmAdapter
import com.example.myapplication.adapter.GenreAdapter
import com.example.myapplication.data.Film
import com.example.myapplication.data.Genre
import com.example.myapplication.viewmodel.FilmViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmListFragment : Fragment() {

    private val filmViewModel: FilmViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var genreRecyclerView: RecyclerView
    private lateinit var filmAdapter: FilmAdapter
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var errorLayout: LinearLayout
    private lateinit var errorMessageTextView: TextView
    private lateinit var retryButton: Button
    private lateinit var filmsTextView: TextView
    private lateinit var genresTextView: TextView
    private var allFilms: List<Film> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("FilmListFragment", "onCreateView called")
        return inflater.inflate(R.layout.fragment_film_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //присваиваем элементы из xml
        recyclerView = view.findViewById(R.id.filmRecyclerView)
        genreRecyclerView = view.findViewById(R.id.genreRecyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        errorLayout = view.findViewById(R.id.errorLayout)
        errorMessageTextView = view.findViewById(R.id.errorMessageTextView)
        retryButton = view.findViewById(R.id.retryButton)
        filmsTextView = view.findViewById(R.id.textView3)
        genresTextView = view.findViewById(R.id.textView)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        genreRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        //скрытие и показ элементов
        filmViewModel.films.observe(viewLifecycleOwner, Observer { films ->
            progressBar.visibility = View.GONE
            errorLayout.visibility = View.GONE
            filmsTextView.visibility = View.VISIBLE
            genresTextView.visibility = View.VISIBLE
            allFilms = films

            //сорт по имени
            val sortedFilms = films.sortedBy { it.localized_name }
            filmAdapter = FilmAdapter(sortedFilms) { film ->
                //обр клика
                val bundle = Bundle().apply {
                    putParcelable("film", film)
                }
                val filmDetailsFragment = FilmDetailsFragment().apply {
                    arguments = bundle
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, filmDetailsFragment)
                    .addToBackStack(null)
                    .commit()
            }
            recyclerView.adapter = filmAdapter

            val genres = films.flatMap { it.genres }.distinct().map { Genre(it) }
            //адапт для жанров
            genreAdapter = GenreAdapter(genres) { selectedGenre ->
                filterFilmsByGenre(selectedGenre.name)
            }
            genreRecyclerView.adapter = genreAdapter
        })

        //скрытие и показ элементов
        filmViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            progressBar.visibility = View.GONE
            errorLayout.visibility = View.VISIBLE
            filmsTextView.visibility = View.GONE
            genresTextView.visibility = View.GONE
            errorMessageTextView.text = errorMessage
        })

        retryButton.setOnClickListener {
            errorLayout.visibility = View.GONE
            filmsTextView.visibility = View.GONE
            genresTextView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            filmViewModel.fetchFilms()
        }

        if (savedInstanceState == null) {
            progressBar.visibility = View.VISIBLE
            filmsTextView.visibility = View.GONE
            genresTextView.visibility = View.GONE
            filmViewModel.fetchFilms()
        } else {
            //вос сост после поворота
            progressBar.visibility = View.GONE
            errorLayout.visibility = View.GONE
            filmsTextView.visibility = View.VISIBLE
            genresTextView.visibility = View.VISIBLE
        }
    }

    private fun filterFilmsByGenre(selectedGenre: String) {
        val filteredFilms = if (selectedGenre.isEmpty()) {
            allFilms
        } else {
            allFilms.filter { it.genres.contains(selectedGenre) }
        }
        filmAdapter = FilmAdapter(filteredFilms) { film ->
            //обработка клик
            val bundle = Bundle().apply {
                putParcelable("film", film)
            }
            val filmDetailsFragment = FilmDetailsFragment().apply {
                arguments = bundle
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, filmDetailsFragment)
                .addToBackStack(null)
                .commit()
        }
        recyclerView.adapter = filmAdapter
    }
}





