package com.raaf.pokeclient.data

import com.raaf.pokeclient.data.api.PokeService
import com.raaf.pokeclient.data.dataModels.Pokemon
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import javax.inject.Inject

class RandomPokemonProvider @Inject constructor(val pokeService: PokeService)  {

    private var compositeDisposable: CompositeDisposable? = null
    var currentRandomPokemonFlowable: Subject<Pokemon?> = PublishSubject.create()

    companion object {
        private val ID_PRIMARY = 1 to 898
        private val ID_SECONDARY = 10001 to 10228
        private val ID_LIST: List<Int> = (ID_PRIMARY.first..ID_PRIMARY.second).toList() +
                (ID_SECONDARY.first..ID_SECONDARY.second).toList()
    }

    init {
        compositeDisposable = CompositeDisposable()
    }

    private fun calculateRandomId() : Int {
        return ID_LIST.random()
    }

    fun getPokemon(id: Int?) {
        compositeDisposable?.add(pokeService.getPokemonById(id ?: calculateRandomId())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response != null) {
                        currentRandomPokemonFlowable.onNext(response)
                    }
                },
                { t -> getPokemon(id)}
            ))
    }
}