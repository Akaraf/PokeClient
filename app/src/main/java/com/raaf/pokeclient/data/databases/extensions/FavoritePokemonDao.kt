package com.raaf.pokeclient.data.databases.extensions

import com.raaf.pokeclient.data.dataModels.Pokemon
import com.raaf.pokeclient.data.databases.favoritePokemon.FavoritePokemonDao
import com.raaf.pokeclient.utils.onFailure
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

fun FavoritePokemonDao.addToFavorite(pokemon: Pokemon) {
    Completable.fromAction {
        this.insert(pokemon)
    }
        .subscribeOn(Schedulers.io())
        .subscribe({}, { onFailure(it) })
}

fun FavoritePokemonDao.removeFromFavorite(pokemon: Pokemon) {
    Completable.fromAction {
        this.delete(pokemon)
    }
        .subscribeOn(Schedulers.io())
        .subscribe({}, { onFailure(it) })
}