package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.myapplication.ui.FilmListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            // Заменяем контейнер на FilmListFragment
            supportFragmentManager.commit {
                replace(R.id.fragment_container, FilmListFragment())
            }
        }
    }
}
