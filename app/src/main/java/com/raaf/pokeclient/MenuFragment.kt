package com.raaf.pokeclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.raaf.pokeclient.utils.setTitle

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
        searchButton.setOnClickListener(this)
        randomButton = view.findViewById(R.id.random_button)
        randomButton.setOnClickListener(this)
        favoritesButton = view.findViewById(R.id.favorites_button)
        favoritesButton.setOnClickListener(this)
        return view
    }

    override fun onResume() {
        super.onResume()
        setTitle(requireActivity().findViewById(R.id.toolbar), getString(R.string.menu_fragment_label))
    }

    override fun onClick(p0: View?) {
        when (p0) {
            searchButton -> {
                findNavController().navigate(R.id.action_menuFragment_to_searchFragment)
            }
            randomButton -> {

            }
            favoritesButton -> {

            }
        }
    }
}