package com.raaf.pokeclient.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.raaf.pokeclient.R
import com.raaf.pokeclient.data.dataModels.Pokemon
import java.util.*

fun fillUI(layout: CardView,
           image: ImageView,
           name: TextView,
           id: TextView,
           characteristic: TextView,
           favorite: TextView,
           pokemon: Pokemon?
) {
    if (pokemon == null) return
    Glide.with(image)
        .load(pokemon.sprites.other.home.front_default)
        .error(ColorDrawable(Color.GRAY))
        .into(image)
    name.text = pokemon.name.replaceFirstChar {
        if (it.isLowerCase())it.titlecase(Locale.getDefault())
        else it.toString() }
    id.text = "(${pokemon.id})"
    characteristic.text = "EXP: ${pokemon.baseExperience}   " +
            "HEIGHT: ${pokemon.height}   " +
            "WEIGHT: ${pokemon.weight}"
    favorite.text = favorite.context.getString(R.string.add_to_favorite)
    layout.visibility = VISIBLE
}

fun clearUI(layout: CardView,
            image: ImageView,
            name: TextView,
            id: TextView,
            characteristic: TextView,
            favorite: TextView
) {
    layout.visibility = GONE
    name.text = ""
    id.text = ""
    image.setImageDrawable(ColorDrawable(Color.GRAY))
    characteristic.text = ""
    favorite.text = ""
}

fun isAddingToFavorite(isAdding: Boolean, textView: TextView) {
    if (isAdding) textView.text = textView.context.getString(R.string.remove_from_favorite)
    else textView.text = textView.context.getString(R.string.add_to_favorite)
}