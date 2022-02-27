package com.raaf.pokeclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.raaf.pokeclient.data.dataModels.Pokemon
import com.raaf.pokeclient.extensions.hideKeyboard
import com.raaf.pokeclient.extensions.lazyViewModel
import com.raaf.pokeclient.utils.*
import com.raaf.pokeclient.viewModels.SearchViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class SearchFragment : Fragment(), View.OnClickListener {

    val searchVM: SearchViewModel by lazyViewModel {
        App.pokeComponent.searchViewModel().create(it)
    }

    private val searchLayout: View by lazy {
        requireActivity().layoutInflater
            .inflate(R.layout.layout_search_toolbar, null)
    }
    private val searchET: EditText by lazy {
        searchLayout.findViewById(R.id.search_e_t)
    }
    private val searchAction: ImageView by lazy {
        searchLayout.findViewById(R.id.search_action_i_v)
    }

    private lateinit var searchedPokemonLayout: CardView
    private lateinit var pokemonIV: ImageView
    private lateinit var pokemonNameTV: TextView
    private lateinit var pokemonIdTV: TextView
    private lateinit var pokemonCharacteristicTV: TextView
    private lateinit var favoriteTV: TextView

    private var isViewAdded = false
    private var compositeDisposable: CompositeDisposable? = null
    private var favoriteStatusDisposable: Disposable? = null
    private var isPokemonInFavorite = false

    private var pokemon: Pokemon? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        searchedPokemonLayout = view.findViewById(R.id.layout_searched_pokemon)
        pokemonIV = searchedPokemonLayout.findViewById(R.id.pokemon_i_v)
        pokemonNameTV = searchedPokemonLayout.findViewById(R.id.pokemon_name_t_v)
        pokemonIdTV = searchedPokemonLayout.findViewById(R.id.pokemon_id_t_v)
        pokemonCharacteristicTV = searchedPokemonLayout
            .findViewById(R.id.pokemon_characteristics_t_v)
        favoriteTV = searchedPokemonLayout.findViewById(R.id.favorite_t_v)
        favoriteTV.setOnClickListener(this)
        return view
    }

    override fun onStart() {
        super.onStart()
        if (!isViewAdded) {
            searchET.setOnEditorActionListener { view, i, keyEvent ->
                if (i == EditorInfo.IME_ACTION_DONE) {
                    makeActionSearch()
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
            searchAction.setOnClickListener(this)
            addView(requireActivity().findViewById(R.id.toolbar), searchLayout)
            isViewAdded = true
            searchVM.getSavedSearchName()?.let { searchPokemon(it) }
        }
    }

    override fun onStop() {
        super.onStop()
        searchVM.saveSearchName()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dispose()
    }

    override fun onClick(p0: View?) {
        when(p0) {
            searchAction -> {
                makeActionSearch()
            }
            favoriteTV -> {
                if (isPokemonInFavorite) pokemon?.let { searchVM.removeFromFavorite(it) }
                else pokemon?.let { searchVM.addToFavorite(it) }
                isPokemonInFavorite = !isPokemonInFavorite
            }
        }
    }

    private fun makeActionSearch() {
        val searchText = searchET.text.toString().lowercase(Locale.getDefault())
        if (searchText.isNotEmpty()) {
            searchPokemon(searchText)
            searchET.text.clear()
            searchET.clearFocus()
        }
        hideKeyboard()
    }

    private fun fillPokemonUI(pokemon: Pokemon?) {
        fillUI(
            searchedPokemonLayout,
            pokemonIV,
            pokemonNameTV,
            pokemonIdTV,
            pokemonCharacteristicTV,
            favoriteTV,
            pokemon
        )
    }

    private fun dispose() {
        compositeDisposable?.dispose()
        compositeDisposable = null
        favoriteStatusDisposable?.dispose()
        favoriteStatusDisposable = null
    }

    private fun searchPokemon(name: String) {
        clearUI(
            searchedPokemonLayout,
            pokemonIV,
            pokemonNameTV,
            pokemonIdTV,
            pokemonCharacteristicTV,
            favoriteTV
        )
        if (compositeDisposable == null) compositeDisposable = CompositeDisposable()
        val pokemonDisposable = searchVM.searchPokemon(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    pokemon = response
                    fillPokemonUI(pokemon)
                    pokemon?.id?.let { observePokemonInFavorite(it) }
                },
                {t -> onFailure(t) })
        compositeDisposable?.add(pokemonDisposable)
    }

    private fun observePokemonInFavorite(id: Int) {
        favoriteStatusDisposable?.dispose()
        favoriteStatusDisposable = searchVM.dao.checkItem(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                if (response != null) isPokemonInFavorite = response.isNotEmpty()
                isAddingToFavorite(isPokemonInFavorite, favoriteTV)
            }
    }
}