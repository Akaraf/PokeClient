package com.raaf.pokeclient.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.raaf.pokeclient.data.databases.favoritePokemon.FavoritePokemonDatabase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class FavoritesViewModel @AssistedInject constructor(
    private val favoriteDB: FavoritePokemonDatabase,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    val favoriteDao = favoriteDB.favoritePokemonDao()

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle) : FavoritesViewModel
    }
}