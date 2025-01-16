package com.example.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.FilmAdapter
import com.example.myapplication.viewmodel.FilmViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel




class FilmListFragment : Fragment() {

    private val filmViewModel: FilmViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var filmAdapter: FilmAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_film_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        filmViewModel.films.observe(viewLifecycleOwner, Observer { films ->
            filmAdapter = FilmAdapter(films)
            recyclerView.adapter = filmAdapter
        })

        filmViewModel.fetchFilms()
    }
}
