package com.raaf.pokeclient.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.raaf.pokeclient.data.RandomPokemonProvider
import com.raaf.pokeclient.data.dataModels.Pokemon
import com.raaf.pokeclient.data.databases.favoritePokemon.FavoritePokemonDatabase
import com.raaf.pokeclient.utils.onFailure
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.Subject

class RandomPokemonViewModel @AssistedInject constructor(
    private val randomPokemonProvider: RandomPokemonProvider,
    private val favoriteDB: FavoritePokemonDatabase,
    @Assisted val savedStateHandle: SavedStateHandle

): ViewModel() {

    companion object {
        private const val RANDOM_ID_STATE = "random id state"
    }

    var savedRandomId: Int? = null
    var savedPokemon: Pokemon? = null
    var dao = favoriteDB.favoritePokemonDao()

    init {
        setSavedRandomId()
    }

    fun getRandomPokemon() {
        randomPokemonProvider.getPokemon(null)
    }

    private fun setSavedRandomId() {
        val saved = savedStateHandle.get<Int?>(RANDOM_ID_STATE)
        savedRandomId = saved
        randomPokemonProvider.getPokemon(saved)
    }

    fun saveRandomId(pokemon: Pokemon?) {
        savedPokemon = pokemon
        savedStateHandle.set(RANDOM_ID_STATE, pokemon?.id)
    }

    fun addToFavorite(pokemon: Pokemon) {
        Completable.fromAction {
            favoriteDB.favoritePokemonDao().insert(pokemon)
        }
            .subscribeOn(Schedulers.io())
            .subscribe({}, { onFailure(it) })
    }

    fun removeFromFavorite(pokemon: Pokemon) {
        Completable.fromAction {
            favoriteDB.favoritePokemonDao().delete(pokemon)
        }
            .subscribeOn(Schedulers.io())
            .subscribe({}, { onFailure(it) })
    }

    fun getObservableRandomPokemon() : Subject<Pokemon?> {
        return randomPokemonProvider.currentRandomPokemonFlowable
    }

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle) : RandomPokemonViewModel
    }
}