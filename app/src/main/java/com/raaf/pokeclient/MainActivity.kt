package com.raaf.pokeclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.raaf.pokeclient.utils.removeViews

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_PokeClient)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)
        toolbar.setNavigationOnClickListener {
            navController.popBackStack()
            removeViews(toolbar)
        }
    }

    override fun onBackPressed() {
        removeViews(toolbar)
        super.onBackPressed()
    }
}