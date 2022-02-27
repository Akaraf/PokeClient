package com.raaf.pokeclient.utils

import android.view.View
import androidx.appcompat.widget.Toolbar

    fun setTitle(toolbar: Toolbar, title: String) {
        toolbar.title = title
        toolbar.visibility = View.VISIBLE
    }

    fun addView(toolbar: Toolbar, view: View) {
        toolbar.addView(view)
    }

    fun removeViews(toolbar: Toolbar) {
        toolbar.removeAllViews()
    }