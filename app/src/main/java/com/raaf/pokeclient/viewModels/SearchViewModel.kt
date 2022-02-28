package com.raaf.pokeclient.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.raaf.pokeclient.data.api.PokeService
import com.raaf.pokeclient.data.dataModels.Pokemon
import com.raaf.pokeclient.data.databases.favoritePokemon.FavoritePokemonDatabase
import com.raaf.pokeclient.utils.onFailure
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchViewModel @AssistedInject constructor(
    private val pokeService: PokeService,
    private val favoriteDB: FavoritePokemonDatabase,
    @Assisted val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val SEARCH_NAME_STATE = "search name state"
    }

    private var savedSearchName: String? = null
    var dao = favoriteDB.favoritePokemonDao()

    init {
        setSavedSearchName()
    }

    fun searchPokemon(searchName: String) : Observable<Pokemon> {
        savedSearchName = searchName
        return pokeService.getPokemonByName(searchName)
    }

    private fun setSavedSearchName() {
        val savedName = savedStateHandle.get<String?>(SEARCH_NAME_STATE)
        if (savedName != null) searchPokemon(savedName)
    }

    fun getSavedSearchName() : String? {
        return savedSearchName
    }

    fun saveSearchName() {
        savedStateHandle.set(SEARCH_NAME_STATE, savedSearchName)
    }

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle) : SearchViewModel
    }
}