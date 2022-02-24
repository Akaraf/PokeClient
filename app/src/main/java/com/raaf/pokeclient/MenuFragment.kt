package com.raaf.pokeclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class MenuFragment : Fragment(), View.OnClickListener {

    private lateinit var searchButton: Button
    private lateinit var randomButton: Button
    private lateinit var favoritesButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        searchButton = view.findViewById(R.id.search_button)
        randomButton = view.findViewById(R.id.random_button)
        favoritesButton = view.findViewById(R.id.favorites_button)
        return view
    }

    override fun onClick(p0: View?) {
        when (p0) {
            searchButton -> {

            }
            randomButton -> {

            }
            favoritesButton -> {

            }
        }
    }
}